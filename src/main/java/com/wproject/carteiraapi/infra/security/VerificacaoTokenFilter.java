package com.wproject.carteiraapi.infra.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.annotations.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import com.wproject.carteiraapi.model.Usuario;
import com.wproject.carteiraapi.repository.UsuarioRepository;
import com.wproject.carteiraapi.service.TokenService;


public class VerificacaoTokenFilter extends OncePerRequestFilter{
	
	
	private TokenService service;
	private UsuarioRepository repository;
	
	public VerificacaoTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
		this.service = tokenService;
		this.repository = usuarioRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = request.getHeader("Authorization");
		if(token == null || token.trim().isEmpty()) {
			filterChain.doFilter(request, response);
			return;
		}
		
		token = token.replace("Bearer ", "");
		
		boolean tokenValid = service.isValid(token);
		if (tokenValid) {
			Long idUsuario = service.extrairId(token);
			Usuario logado = repository.carregarIdComPerfil(idUsuario).get();
			Authentication auth  = new UsernamePasswordAuthenticationToken(logado, null, logado.getAuthorities());
			
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		
		filterChain.doFilter(request, response);
	}

}
