package com.wproject.carteiraapi.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.tomcat.jni.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	public Page<TransacaoDto> listar(Pageable paginacao) {
		Page<Transacao> transacoes = repository.findAll(paginacao);
		
		return transacoes.map(transacao -> modelMapper.map(transacao, TransacaoDto.class)); 
	}
	
	@Transactional
	public TransacaoDto cadastrar(TransacaoFormDto dto) {
		Long idUsuario = dto.getUsuarioId();
		try {
			Usuario user = userRepository.getById(idUsuario);
			
			Transacao transacao = modelMapper.map(dto, Transacao.class);
			transacao.setId(null);
			transacao.setUsuario(user);
			repository.save(transacao);
			return modelMapper.map(transacao, TransacaoDto.class);			
		} catch (EntityNotFoundException e) {
			throw new IllegalArgumentException("Usuario inexistente");
		}
		
	}

	@Transactional
	public TransacaoDto atualizar(AtualizarTransacaoFormDto dto) {
		Transacao t = repository.getById(dto.getId());
		t.atualizarInformacoes(
				dto.getTicker(),
				dto.getPreco(),
				dto.getQuantidade(),
				dto.getTipo(),
				dto.getData());
		return modelMapper.map(t, TransacaoDto.class);		
	}

	public void remover(@NotNull Long id) {
		repository.deleteById(id);
	}

	public TransacaoDetalhadaDto listarPorId(@NotNull Long id) {
		Transacao t = repository.findById(id).orElseThrow(()->new EntityNotFoundException());
		return modelMapper.map(t, TransacaoDetalhadaDto.class);	
	}
}
