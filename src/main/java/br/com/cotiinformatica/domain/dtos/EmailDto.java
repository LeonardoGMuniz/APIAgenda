package br.com.cotiinformatica.domain.dtos;

import lombok.Data;

@Data

public class EmailDto {

	private String destionatario;
	private String assunto;
	private String mensagem;

}
