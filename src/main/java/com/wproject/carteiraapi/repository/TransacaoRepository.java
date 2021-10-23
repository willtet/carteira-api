package com.wproject.carteiraapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wproject.carteiraapi.dto.ItemCarteiraDto;
import com.wproject.carteiraapi.model.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

	@Query("select new com.wproject.carteiraapi.dto.ItemCarteiraDto( "
			+ "t.ticker, "
			+ "sum(CASE WHEN(t.tipo = 'COMPRA') THEN t.quantidade ELSE (t.quantidade * -1) END), "
			+ "(select sum(CASE WHEN(t2.tipo = 'COMPRA') THEN t2.quantidade ELSE (t2.quantidade * -1) END) from Transacao t2)) "
			+ "from Transacao t "
			+ "group by t.ticker")
	List<ItemCarteiraDto> gerarRelatorioEmPorcentagem();
}
