package com.wproject.carteiraapi.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.wproject.carteiraapi.model.TipoTransacao;
import com.wproject.carteiraapi.model.Transacao;
import com.wproject.carteiraapi.model.Usuario;

class CalculadoraDeImpostoServiceTest {

	@Test
	void transacaoDoTipoCompraNaoDeveriaTerImposto() {
		Transacao t = new Transacao(
				120l,
				"BBSS3",
				new BigDecimal("30"),
				10,
				LocalDate.now(),
				TipoTransacao.COMPRA,
				new Usuario(1l, "Will", "will@email.com","123456"));
		
		CalculadoraDeImpostoService calcula = new CalculadoraDeImpostoService();
		BigDecimal resultado  = calcula.calcular(t);
		assertEquals(BigDecimal.ZERO,resultado);
	}
	
	@Test
	void transacaoMenorQueVinteMilNaoDeveriaTerImposto() {
		Transacao t = new Transacao(
				120l,
				"BBSS3",
				new BigDecimal("30"),
				10,
				LocalDate.now(),
				TipoTransacao.VENDA,
				new Usuario(1l, "Will", "will@email.com","123456"));
		
		CalculadoraDeImpostoService calcula = new CalculadoraDeImpostoService();
		BigDecimal resultado  = calcula.calcular(t);
		assertEquals(BigDecimal.ZERO,resultado);
	}
	
	@Test
	void transacaoAcimaVinteMilDeveriaTerImposto() {
		Transacao t = new Transacao(
				120l,
				"BBSS3",
				new BigDecimal("1000.00"),
				30,
				LocalDate.now(),
				TipoTransacao.VENDA,
				new Usuario(1l, "Will", "will@email.com","123456"));
		
		CalculadoraDeImpostoService calcula = new CalculadoraDeImpostoService();
		BigDecimal resultado  = calcula.calcular(t);
		assertEquals(new BigDecimal("4500.00"),resultado);
	}

}
