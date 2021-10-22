package com.wproject.carteiraapi.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.wproject.carteiraapi.dto.ItemCarteiraDto;
import com.wproject.carteiraapi.model.TipoTransacao;
import com.wproject.carteiraapi.model.Transacao;
import com.wproject.carteiraapi.model.Usuario;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
class TransacaoRepositoryTest {
	
	@Autowired
	private TransacaoRepository repository;
	
	@Autowired
	private TestEntityManager em;

	@Test
	void deveriaRetornarRelatorioDeCarteiraDeInvestimentos() {
		
		Usuario usuario = new Usuario("Rafaela", "rafa@email.com", "123456");
		em.persist(usuario);
		
		Transacao t1 = new Transacao(
				"ITSA4",
				new BigDecimal("10.00"),
				50,
				LocalDate.now(),
				TipoTransacao.COMPRA,
				usuario);
		
		em.persist(t1);
		
		Transacao t2 = new Transacao(
				"BBSE3",
				new BigDecimal("22.80"),
				80,
				LocalDate.now(),
				TipoTransacao.COMPRA,
				usuario);
		
		em.persist(t2);
		
		Transacao t3 = new Transacao(
				"EGIE3",
				new BigDecimal("40.00"),
				25,
				LocalDate.now(),
				TipoTransacao.COMPRA,
				usuario);
		
		em.persist(t3);
		
		Transacao t4 = new Transacao(
				"ITSA4",
				new BigDecimal("11.20"),
				40,
				LocalDate.now(),
				TipoTransacao.COMPRA,
				usuario);
		
		em.persist(t4);
		
		Transacao t5 = new Transacao(
				"SAPR4",
				new BigDecimal("4.02"),
				120,
				LocalDate.now(),
				TipoTransacao.COMPRA,
				usuario);
		
		em.persist(t5);
		
		
		
		
		
		List<ItemCarteiraDto> relatorio = repository.gerarRelatorioEmPorcentagem();
		Assertions
			.assertThat(relatorio)
			.hasSize(4)
			.extracting(
					ItemCarteiraDto::getTicker,
					ItemCarteiraDto::getQuantidade, 
					ItemCarteiraDto::getPercentual)
			.containsExactlyInAnyOrder(
					Assertions.tuple("ITSA4", 90l, 0.285714),
					Assertions.tuple("BBSE3", 80l, 0.253968),
					Assertions.tuple("EGIE3", 25l, 0.079365),
					Assertions.tuple("SAPR4", 120l, 0.380952)
					);
		
		
		
	}

}
