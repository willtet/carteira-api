package com.wproject.carteiraapi.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.tomcat.jni.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wproject.carteiraapi.dto.AtualizarTransacaoFormDto;
import com.wproject.carteiraapi.dto.TransacaoDetalhadaDto;
import com.wproject.carteiraapi.dto.TransacaoDto;
import com.wproject.carteiraapi.dto.TransacaoFormDto;
import com.wproject.carteiraapi.model.Transacao;
import com.wproject.carteiraapi.model.Usuario;
import com.wproject.carteiraapi.repository.TransacaoRepository;
import com.wproject.carteiraapi.repository.UsuarioRepository;

import javassist.NotFoundException;

@Service
public class TransacaoService {
	@Autowired
	private TransacaoRepository repository;
	
	@Autowired
	private UsuarioRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CalculadoraDeImpostoService calculadoraService;
	
	public Page<TransacaoDto> listar(Pageable paginacao, Usuario user) {
		return  repository.findAllByUsuario(paginacao, user).map((transacao) -> modelMapper.map(transacao, TransacaoDto.class));
		
//		List<TransacaoDto> listaTransacaoDto = new ArrayList<>();
//		transacoes.forEach((transacao)->{
//			BigDecimal imposto = calculadoraService.calcular(transacao);
//			TransacaoDto dto = modelMapper.map(transacao, TransacaoDto.class); 
//			dto.setImposto(imposto);
//			listaTransacaoDto.add(dto);
//		});
//		
//		return new PageImpl<TransacaoDto>(
//				listaTransacaoDto,
//				transacoes.getPageable(),
//				transacoes.getTotalElements()); 
	}
	
	@Transactional
	public TransacaoDto cadastrar(TransacaoFormDto dto, Usuario logado) {
		Long idUsuario = dto.getUsuarioId();
		try {
			Usuario user = userRepository.getById(idUsuario);
			if(!user.equals(logado)) {
				lancarAcessoNegado();
			}
			Transacao transacao = modelMapper.map(dto, Transacao.class);
			transacao.setId(null);
			transacao.setUsuario(user);
			
			BigDecimal imposto = calculadoraService.calcular(transacao);
			transacao.setImposto(imposto);
			repository.save(transacao);
			return modelMapper.map(transacao, TransacaoDto.class);			
		} catch (EntityNotFoundException e) {
			throw new IllegalArgumentException("Usuario inexistente");
		}
		
	}

	@Transactional
	public TransacaoDto atualizar(AtualizarTransacaoFormDto dto, Usuario user) {
		Transacao t = repository.getById(dto.getId());
		if(!t.equalUsuario(user)) {
			lancarAcessoNegado();
		}
		t.atualizarInformacoes(
				dto.getTicker(),
				dto.getPreco(),
				dto.getQuantidade(),
				dto.getTipo(),
				dto.getData());
		return modelMapper.map(t, TransacaoDto.class);		
	}

	

	public void remover(@NotNull Long id, Usuario user) {
		Transacao t = repository.getById(id);
		if (!t.equalUsuario(user)) {
			lancarAcessoNegado();
		}
		repository.deleteById(id);
	}

	public TransacaoDetalhadaDto listarPorId(@NotNull Long id, Usuario user) {
		Transacao t = repository.findById(id).orElseThrow(()->new EntityNotFoundException());
		
		if (!t.equalUsuario(user)) {
			lancarAcessoNegado();
		}
		return modelMapper.map(t, TransacaoDetalhadaDto.class);	
	}
	
	private void lancarAcessoNegado() {
		throw new AccessDeniedException("acesso negado");
	}
}
