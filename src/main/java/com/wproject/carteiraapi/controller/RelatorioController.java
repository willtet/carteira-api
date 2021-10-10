package com.wproject.carteiraapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wproject.carteiraapi.dto.ItemCarteiraDto;
import com.wproject.carteiraapi.service.RelatorioService;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {
	
	@Autowired
	RelatorioService service;
	
	@GetMapping("/carteira")
	public List<ItemCarteiraDto> listaRelatorios(){
		return service.gerarListaRelatorio();
	}

}
