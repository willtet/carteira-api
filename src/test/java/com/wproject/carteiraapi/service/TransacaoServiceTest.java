package com.wproject.carteiraapi.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.wproject.carteiraapi.dto.TransacaoDto;
import com.wproject.carteiraapi.dto.TransacaoFormDto;
import com.wproject.carteiraapi.model.TipoTransacao;
import com.wproject.carteiraapi.model.Transacao;
import com.wproject.carteiraapi.model.Usuario;
import com.wproject.carteiraapi.repository.TransacaoRepository;
import com.wproject.carteiraapi.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {
	
	@Mock
	private TransacaoRepository repository;
	
	@Mock
	private UsuarioRepository userRepository;
	
	@Mock
	private ModelMapper mapper;
	
	@Mock
	private CalculadoraDeImpostoService calculadoraService;
	
	@InjectMocks
	private TransacaoService service;
	
	private Usuario logado;

	@BeforeEach
	public void criarUsuario() {
		this.logado = new Usuario("Willian", "willtet", "123456");
	}
	
	@Test
	void deveriaCadastrarTransacao() {
		TransacaoFormDto form = criarTransacaoForm();
		Mockito.when(userRepository.getById(form.getUsuarioId())).thenReturn(logado);
		Transacao transacao = new Transacao(form.getTicker(), form.getPreco(), form.getQuantidade(), form.getData(), form.getTipo(), logado);
		Mockito.when(mapper.map(form,Transacao.class)).thenReturn(transacao);
		Mockito.when(mapper.map(transacao,TransacaoDto.class)).thenReturn(new TransacaoDto(null, transacao.getTicker(),transacao.getPreco(), transacao.getQuantidade(), transacao.getTipo(), BigDecimal.ZERO) );
		
		TransacaoDto dto = service.cadastrar(form, logado);
		Mockito.verify(repository).save(Mockito.any());
		
		
		assertEquals(form.getTicker(),dto.getTicker());
	}
	
	@Test
	void naoDeveriaCadastrarTransacaoSemTerUsuarioCadastrado() {
		TransacaoFormDto form = criarTransacaoForm();
		Mockito
		.when(userRepository.getById(form.getUsuarioId()))
		.thenThrow(EntityNotFoundException.class);
				
		assertThrows(IllegalArgumentException.class, () -> service.cadastrar(form, logado));
		
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
