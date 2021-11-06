package com.wproject.carteiraapi.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.wproject.carteiraapi.model.TipoTransacao;
import com.wproject.carteiraapi.model.Transacao;
import com.wproject.carteiraapi.model.Usuario;

class CalculadoraDeImpostoServiceTest {
	
	CalculadoraDeImpostoService calcula ;
	
	@BeforeEach
	public void init() {
		calcula = new CalculadoraDeImpostoService();
		
	}

	@Test
	void transacaoDoTipoCompraNaoDeveriaTerImposto() {
		Transacao t = criarTransacao(new BigDecimal("30"), 10, TipoTransacao.COMPRA);
		
		BigDecimal resultado  = calcula.calcular(t);
		assertEquals(BigDecimal.ZERO,resultado);
	}
	
	@Test
	void transacaoMenorQueVinteMilNaoDeveriaTerImposto() {
		Transacao t = criarTransacao(new BigDecimal("30"), 10,TipoTransacao.VENDA);

		BigDecimal resultado  = calcula.calcular(t);
		assertEquals(BigDecimal.ZERO,resultado);
	}
	
	@Test
	void transacaoAcimaVinteMilDeveriaTerImposto() {
		Transacao t = criarTransacao(new BigDecimal("1000.00"), 30, TipoTransacao.VENDA);

		BigDecimal resultado  = calcula.calcular(t);
		assertEquals(new BigDecimal("4500.00"),resultado);
	}

	private Transacao criarTransacao(BigDecimal preco, int quantidade, TipoTransacao tipo) {
		Transacao t = new Transacao(
				120l,
				"BBSS3",
				preco,
				quantidade,
				LocalDate.now(),
				tipo,
				new Usuario(1l, "Will", "will@email.com","123456", null));
		return t;
	}

}
