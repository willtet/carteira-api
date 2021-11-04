package com.wproject.carteiraapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.wproject.carteiraapi.model.Usuario;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	@Value("${jjwt.secret}")
	private String secret;

	public String gerarToken(Authentication authentication) {
		Usuario user = (Usuario) authentication.getPrincipal();
		return Jwts
				.builder()
				.setId(user.getId().toString())
				.signWith(SignatureAlgorithm.HS256, "sdmskdmskdmskmd")
				.compact();
	}

}
