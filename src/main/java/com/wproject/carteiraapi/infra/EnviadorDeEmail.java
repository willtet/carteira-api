package com.wproject.carteiraapi.infra;

public interface EnviadorDeEmail {
	void enviarEmail(String destinatario,
			String assunto,
			String mensagem);
}