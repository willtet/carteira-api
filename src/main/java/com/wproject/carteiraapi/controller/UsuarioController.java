package com.wproject.carteiraapi.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.wproject.carteiraapi.dto.UsuarioDto;
import com.wproject.carteiraapi.dto.UsuarioFormDto;
import com.wproject.carteiraapi.model.Usuario;
import com.wproject.carteiraapi.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	
	
	@Autowired
	private UsuarioService service;
	
	@GetMapping
	public Page<UsuarioDto> ler(@PageableDefault(size=20) Pageable paginacao) {
		return service.ler(paginacao);
	}

	@PostMapping
	public ResponseEntity<UsuarioDto> cadastrar(
			@RequestBody @Valid UsuarioFormDto dto,
			UriComponentsBuilder builder) {
		UsuarioDto usuarioDto = service.cadastrar(dto);
		URI uri = builder
				.path("/usuario/{id}")
				.buildAndExpand(usuarioDto.getId())
				.toUri();
		return ResponseEntity.created(uri).body(usuarioDto);
	}
}
