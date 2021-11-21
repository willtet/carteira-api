package com.wproject.carteiraapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wproject.carteiraapi.dto.UsuarioDto;
import com.wproject.carteiraapi.dto.UsuarioFormDto;
import com.wproject.carteiraapi.infra.EnviadorDeEmail;
import com.wproject.carteiraapi.model.Perfil;
import com.wproject.carteiraapi.model.Usuario;
import com.wproject.carteiraapi.repository.PerfilRepository;
import com.wproject.carteiraapi.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private EnviadorDeEmail enviadorEmail;
	
	public Page<UsuarioDto> ler(Pageable page) {
		Page<Usuario> usuarios = repository.findAll(page);
		return usuarios.map(usuario -> modelMapper.map(usuario, UsuarioDto.class));

	}

	@Transactional
	public UsuarioDto cadastrar(UsuarioFormDto dto) {
		Usuario user = modelMapper.map(dto, Usuario.class);
		Perfil perfil = perfilRepository.getById(dto.getPerfilId());
		user.adicionarPerfil(perfil);
		
		String senha = String.valueOf(new Random().nextInt(999999));
		user.setSenha(encoder.encode(senha));
		repository.save(user);
		enviadorEmail.enviarEmail(user.getEmail(), "Não retorne", senha);
		return modelMapper.map(user, UsuarioDto.class);
		
	}
}
