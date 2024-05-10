package br.com.cotiinformatica.domain.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CriarPessoaRequestDto {

	@Size(min = 8, max = 100, message = "Informe o nome de 8 a 100 caracteres.")
	@NotEmpty(message = "Por favor, informe o nome da pessoa.")
	private String nome;
	
	@Email(message = "Por favor, informe um endereço de email válido.")
	@NotEmpty(message = "Por favor, informe o email da pessoa.")
	private String email;
	
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$", 
			message = "Por favor, informe a senha com letras maiúsculas, minúsculas, números, símbolos e pelo menos 8 caracteres.")
	@NotEmpty(message = "Por favor, informe a senha da pessoa.")
	private String senha;
}
