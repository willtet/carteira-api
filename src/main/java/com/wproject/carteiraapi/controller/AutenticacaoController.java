package com.wproject.carteiraapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wproject.carteiraapi.dto.LoginFormDto;
import com.wproject.carteiraapi.dto.TokenDto;
import com.wproject.carteiraapi.infra.security.AutenticacaoService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {
	
	@Autowired
	private AutenticacaoService service;
	
	@PostMapping
	public TokenDto autenticar(@RequestBody LoginFormDto dto) {
		return new TokenDto(service.autenticar(dto));
		
	}
}
