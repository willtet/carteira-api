package com.wproject.carteiraapi.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import com.wproject.carteiraapi.model.TipoTransacao;
import com.wproject.carteiraapi.model.Transacao;

@Service
public class CalculadoraDeImpostoService {
	//15% de imposto para transacao tipo venda com valor superior a 20.000 reais
	public BigDecimal calcular(Transacao t) {
		if(t.getTipo() == TipoTransacao.COMPRA) {
			return BigDecimal.ZERO;
		}
		
		BigDecimal valorTransacao = 
				t.getPreco()
				.multiply(new BigDecimal(t.getQuantidade()));
		
		if(valorTransacao.compareTo(new BigDecimal("20000.00")) < 0) {
			return BigDecimal.ZERO;
		}
		
		return valorTransacao.multiply(new BigDecimal("0.15")).setScale(2,RoundingMode.HALF_UP);
	}
}
