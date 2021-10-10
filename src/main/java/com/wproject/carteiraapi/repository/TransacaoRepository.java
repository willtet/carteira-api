package com.wproject.carteiraapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wproject.carteiraapi.dto.ItemCarteiraDto;
import com.wproject.carteiraapi.model.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

	@Query("select new com.wproject.carteiraapi.dto.ItemCarteiraDto( "
			+ "t.ticker, "
			+ "sum(t.quantidade), "
			+ "sum(t.quantidade) * 1.0 / (select sum(t2.quantidade) from Transacao t2) * 1.0) "
			+ "from Transacao t "
			+ "group by t.ticker")
	List<ItemCarteiraDto> gerarRelatorioEmPorcentagem();
}
