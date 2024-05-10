package br.com.cotiinformatica.domain.dtos;

import java.util.UUID;

import lombok.Data;

@Data
public class AutenticarPessoaResponseDto {

	private UUID id;
	private String nome;
	private String email;
	private String accessToken;
}
