package br.com.cotiinformatica.infrastructure.handlers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import br.com.cotiinformatica.domain.exceptions.AcessoNegadoException;

@ControllerAdvice
public class AcessoNegadoExceptionHandler {
	@ExceptionHandler(AcessoNegadoException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public List<String> errorHandler(AcessoNegadoException e) {
		List<String> errors = new ArrayList<String>();
		errors.add(e.getMessage());
		return errors;
	}
}
