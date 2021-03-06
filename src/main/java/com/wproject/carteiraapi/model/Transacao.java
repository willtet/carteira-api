package com.wproject.carteiraapi.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="transacoes")
public class Transacao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String ticker;
	private BigDecimal preco;
	private Integer quantidade;
	private LocalDate data;
	private BigDecimal imposto;
	
	@Enumerated(EnumType.STRING)
	private TipoTransacao tipo;
	
	@ManyToOne
	private Usuario usuario;

	public Transacao(String ticker, BigDecimal preco, Integer quantidade, LocalDate data, TipoTransacao tipo,
			Usuario usuario) {
		this.ticker = ticker;
		this.preco = preco;
		this.quantidade = quantidade;
		this.data = data;
		this.tipo = tipo;
		this.usuario = usuario;
	}

	public void atualizarInformacoes(String ticker, BigDecimal preco, Integer quantidade, TipoTransacao tipo, LocalDate localDate) {
		this.ticker = ticker;
		this.preco = preco;
		this.quantidade = quantidade;
		this.tipo = tipo;
		this.data = localDate;
		
	}
	
	public boolean equalUsuario(Usuario user) {
		return this.usuario.equals(user);
	}
	
	
	
}
