package com.wproject.carteiraapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wproject.carteiraapi.model.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}
