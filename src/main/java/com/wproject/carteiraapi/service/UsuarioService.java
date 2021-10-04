package com.wproject.carteiraapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wproject.carteiraapi.dto.UsuarioDto;
import com.wproject.carteiraapi.dto.UsuarioFormDto;
import com.wproject.carteiraapi.model.Usuario;
import com.wproject.carteiraapi.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	private ModelMapper modelMapper = new ModelMapper();
	
	public List<UsuarioDto> ler() {
		List<Usuario> usuarios = repository.findAll();
		return usuarios.stream().map(usuario -> modelMapper.map(usuario, UsuarioDto.class)).collect(Collectors.toList());

	}

	public void cadastrar(UsuarioFormDto dto) {
		Usuario user = modelMapper.map(dto, Usuario.class);
		String senha = String.valueOf(new Random().nextInt(999999));
		user.setSenha(senha);
		repository.save(user);
		
	}
}
