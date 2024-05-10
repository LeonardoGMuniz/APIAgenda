package br.com.cotiinformatica.domain.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AutenticarPessoaRequestDto {

	@Email(message = "Por favor, informe um endereço de email válido.")
	@NotEmpty(message = "Por favor, informe o seu email de acesso.")
	private String emailAcesso;
	
	@Size(min = 8, message = "Por favor, informe no mínimo 8 caracteres.")
	@NotEmpty(message =  "Por favor, informe a sua senha de acesso.")
	private String senhaAcesso;
}
