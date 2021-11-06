package com.wproject.carteiraapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import com.wproject.carteiraapi.model.Perfil;
import com.wproject.carteiraapi.model.Usuario;

public interface PerfilRepository extends JpaRepository<Perfil, Long>{

}
