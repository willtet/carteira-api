package com.wproject.carteiraapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.wproject.carteiraapi.model.Usuario;

import io.jsonwebtoken.Claims;
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
				.setSubject(user.getId().toString())
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}
	
	public boolean isValid(String token) {
		try {
			Jwts
			.parser()
			.setSigningKey(secret)
			.parseClaimsJws(token);
			return true;
		} catch(Exception e) {
			System.out.println(e);
			return false;
		}
		
	}

	public Long extrairId(String token) {
		Claims claims = Jwts
							.parser()
							.setSigningKey(secret)
							.parseClaimsJws(token)
							.getBody();
		return Long.parseLong(claims.getSubject());
	}
	
	public static void main(String[] args) {
		
	}

}
