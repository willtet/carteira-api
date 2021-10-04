package com.wproject.carteiraapi.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wproject.carteiraapi.dto.TransacaoDto;
import com.wproject.carteiraapi.dto.TransacaoFormDto;
import com.wproject.carteiraapi.model.Transacao;
import com.wproject.carteiraapi.repository.TransacaoRepository;

@Service
public class TransacaoService {
	@Autowired
	private TransacaoRepository repository;
	private ModelMapper modelMapper = new ModelMapper();
	
	public Page<TransacaoDto> listar(Pageable paginacao) {
		Page<Transacao> transacoes = repository.findAll(paginacao);
		
		return transacoes.map(transacao -> modelMapper.map(transacao, TransacaoDto.class)); 
	}
	
	@Transactional
	public void cadastrar(TransacaoFormDto dto) {
		Transacao transacao = modelMapper.map(dto, Transacao.class);
		transacao.setId(null);
		repository.save(transacao);
	}
}
