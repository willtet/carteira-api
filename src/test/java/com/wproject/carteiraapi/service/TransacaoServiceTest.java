package com.wproject.carteiraapi.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.wproject.carteiraapi.dto.TransacaoDto;
import com.wproject.carteiraapi.dto.TransacaoFormDto;
import com.wproject.carteiraapi.model.TipoTransacao;
import com.wproject.carteiraapi.repository.TransacaoRepository;
import com.wproject.carteiraapi.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {
	
	@Mock
	private TransacaoRepository repository;
	
	@Mock
	private UsuarioRepository userRepository;
	
	@InjectMocks
	private TransacaoService service;

	@Test
	void deveriaCadastrarTransacao() {
		TransacaoFormDto form = criarTransacaoForm();
		
		TransacaoDto dto = service.cadastrar(form);
		Mockito.verify(repository).save(Mockito.any());
		
		
		assertEquals(form.getTicker(),dto.getTicker());
	}
	
	@Test
	void naoDeveriaCadastrarTransacaoSemTerUsuarioCadastrado() {
		TransacaoFormDto form = criarTransacaoForm();
		Mockito
		.when(userRepository.getById(form.getUsuarioId()))
		.thenThrow(EntityNotFoundException.class);
				
		assertThrows(IllegalArgumentException.class, () -> service.cadastrar(form));
		
	}

	private TransacaoFormDto criarTransacaoForm() {
		TransacaoFormDto form = new TransacaoFormDto(
				"BBSS3",
				new BigDecimal("1000.00"),
				30,
				LocalDate.now(),
				TipoTransacao.VENDA,
				1l);
		return form;
	}

}
