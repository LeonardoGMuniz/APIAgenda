package br.com.cotiinformatica.domain.interfaces;

import br.com.cotiinformatica.domain.dtos.AutenticarPessoaRequestDto;
import br.com.cotiinformatica.domain.dtos.AutenticarPessoaResponseDto;
import br.com.cotiinformatica.domain.dtos.CriarPessoaRequestDto;
import br.com.cotiinformatica.domain.dtos.CriarPessoaResponseDto;

public interface PessoaDomainService {

	/*
	 * Método para criar pessoa
	 */
	CriarPessoaResponseDto criar(CriarPessoaRequestDto dto);
	
	/*
	 * Método para autenticar pessoa
	 */
	AutenticarPessoaResponseDto autenticar(AutenticarPessoaRequestDto dto);
}
