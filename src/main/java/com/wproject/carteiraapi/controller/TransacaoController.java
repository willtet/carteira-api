package com.wproject.carteiraapi.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	@PostMapping
	private void cadastrar(@RequestBody @Valid TransacaoFormDto dto) {
		service.cadastrar(dto);
	}
}
