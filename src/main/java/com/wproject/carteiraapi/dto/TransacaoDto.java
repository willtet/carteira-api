package com.wproject.carteiraapi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.wproject.carteiraapi.model.TipoTransacao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransacaoDto {

	private Long id;
	private String ticker;
	private BigDecimal preco;
	private Integer quantidade;
	private TipoTransacao tipo;
	
	
}
