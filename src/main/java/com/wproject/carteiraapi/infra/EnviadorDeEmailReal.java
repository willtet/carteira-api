package com.wproject.carteiraapi.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Profile("prod")
public class EnviadorDeEmailReal implements EnviadorDeEmail{
	@Autowired
	private JavaMailSender sender;
	
	@Async
	public void enviarEmail(
			String destinatario,
			String assunto,
			String mensagem) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(destinatario);
		mail.setSubject(assunto);
		mail.setText(mensagem);
		
		sender.send(mail);
	}
}