package com.wproject.carteiraapi.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wproject.carteiraapi.dto.LoginFormDto;
import com.wproject.carteiraapi.repository.UsuarioRepository;
import com.wproject.carteiraapi.service.TokenService;

@Service
public class AutenticacaoService implements UserDetailsService{
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private TokenService tokenservice;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository
				.findByLogin(username)
				.orElseThrow(() -> new UsernameNotFoundException(""));
	}

	public String autenticar(LoginFormDto dto) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(dto.getLogin(), dto.getSenha());
		authentication = manager.authenticate(authentication);
		return tokenservice.gerarToken(authentication);
	}

}
