package com.wproject.carteiraapi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.wproject.carteiraapi.model.TipoTransacao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransacaoDetalhadaDto extends TransacaoDto{
	private LocalDate data;
	private UsuarioDto usuario;
}
