package br.com.cotiinformatica.domain.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Tarefa {

	@Id
	private UUID id;
	private String nome;
	private LocalDate data;
	private LocalTime hora;
	private Integer prioridade;
	
	@DBRef
	private Pessoa pessoa;
}
