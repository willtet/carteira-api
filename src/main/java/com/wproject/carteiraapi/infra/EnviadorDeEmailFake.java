package com.wproject.carteiraapi.infra;

import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Profile("default")
public class EnviadorDeEmailFake implements EnviadorDeEmail{
	
	@Async
	public void enviarEmail(
			String destinatario,
			String assunto,
			String mensagem) {
		System.out.println("Email Destinatario: "+ destinatario);
		System.out.println("Email Assunto: "+ assunto);
		System.out.println("Email Mensagem: "+ mensagem);
	}
}