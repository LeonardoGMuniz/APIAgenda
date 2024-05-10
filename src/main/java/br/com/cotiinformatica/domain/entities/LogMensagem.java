package br.com.cotiinformatica.domain.entities;

import java.time.LocalTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class LogMensagem {

	@Id
	private UUID id;
	private String status;
	private LocalTime dataHora;
	private String mensagem;
	private String erro;
}
