package br.com.cotiinformatica.domain.exceptions;

public class EmailJaCadastradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmailJaCadastradoException() {
		super("O email informado já está cadastrado. Tente outro.");
	}	
}
