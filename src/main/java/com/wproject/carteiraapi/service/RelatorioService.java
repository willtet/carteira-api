package com.wproject.carteiraapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wproject.carteiraapi.dto.ItemCarteiraDto;
import com.wproject.carteiraapi.repository.TransacaoRepository;

@Service
public class RelatorioService {
	
	@Autowired
	TransacaoRepository repository;

	public List<ItemCarteiraDto> gerarListaRelatorio() {
		// TODO Auto-generated method stub
		return repository.gerarRelatorioEmPorcentagem();
	}

}
