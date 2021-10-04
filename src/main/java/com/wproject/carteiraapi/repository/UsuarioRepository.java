package com.wproject.carteiraapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wproject.carteiraapi.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
}
