package com.wproject.carteiraapi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.wproject.carteiraapi.model.TipoTransacao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoDto {

	private Long id;
	private String ticker;
	private BigDecimal preco;
	private Integer quantidade;
	private TipoTransacao tipo;
	private BigDecimal imposto;
	
}
