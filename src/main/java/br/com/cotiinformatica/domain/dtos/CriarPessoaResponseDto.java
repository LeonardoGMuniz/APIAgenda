package br.com.cotiinformatica.domain.dtos;

import java.util.Date;

import lombok.Data;

@Data

public class CriarPessoaResponseDto {
	
	private String nome;
	private String email;
	private String senha;
	private Date dataHoraCadastro;
	

}
