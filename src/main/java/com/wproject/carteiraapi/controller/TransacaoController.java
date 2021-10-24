package com.wproject.carteiraapi.controller;


import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.wproject.carteiraapi.dto.AtualizarTransacaoFormDto;
import com.wproject.carteiraapi.dto.TransacaoDetalhadaDto;
import com.wproject.carteiraapi.dto.TransacaoDto;
import com.wproject.carteiraapi.dto.TransacaoFormDto;
import com.wproject.carteiraapi.model.Transacao;
import com.wproject.carteiraapi.repository.TransacaoRepository;
import com.wproject.carteiraapi.service.TransacaoService;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

	@Autowired
	private TransacaoService service;
	
	@GetMapping
	private Page<TransacaoDto> listar(@PageableDefault(size=10) Pageable paginacao) {
		return service.listar(paginacao);
	}
	
	@GetMapping("/{id}")
	private ResponseEntity<TransacaoDetalhadaDto> listarById(@PathVariable @NotNull Long id) {
		TransacaoDetalhadaDto t = service.listarPorId(id);
		return ResponseEntity.ok(t);
	}
	
	@PostMapping
	private ResponseEntity<TransacaoDto> cadastrar(
				@RequestBody @Valid TransacaoFormDto dto,
				UriComponentsBuilder builder) {
		
		TransacaoDto transacaoDto = service.cadastrar(dto);
		URI uri = builder
				.path("/transacoes/{id}")
				.buildAndExpand(transacaoDto.getId())
				.toUri();
		return ResponseEntity.created(uri).body(transacaoDto);
	}
	
	@PutMapping
	private ResponseEntity<TransacaoDto> atualizar(@RequestBody @Valid AtualizarTransacaoFormDto dto){
		TransacaoDto transacaoDto = service.atualizar(dto);
		return ResponseEntity.ok(transacaoDto);
		
	}
	
	@DeleteMapping("/{id}")
	private ResponseEntity<TransacaoDto> deletar(@PathVariable @NotNull Long id){
		service.remover(id);
		return ResponseEntity.noContent().build();
		
	}
}
