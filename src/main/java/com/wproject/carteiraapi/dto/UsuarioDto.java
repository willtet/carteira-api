package com.wproject.carteiraapi.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UsuarioDto {
	private Long id;
	private String nome;
	private String login;
}
