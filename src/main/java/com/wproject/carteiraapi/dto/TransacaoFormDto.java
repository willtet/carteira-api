package com.wproject.carteiraapi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.wproject.carteiraapi.model.TipoTransacao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoFormDto {
	
	
	@NotBlank
	@Size(min = 5, max = 6)
	@Pattern(regexp = "[a-zA-Z]{4}[0-9][0-9]?", message = "{ticker.padrao.invalido}")
	private String ticker;
	
	@NotNull
	private BigDecimal preco;
	
	@NotNull
	private int quantidade;
	
	@PastOrPresent
	private LocalDate data;
	
	@NotNull
	private TipoTransacao tipo;
	
	@JsonAlias("usuario_id")
	private Long usuarioId;
}
