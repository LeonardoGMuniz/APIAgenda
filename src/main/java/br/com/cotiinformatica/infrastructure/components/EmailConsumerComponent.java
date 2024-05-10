package br.com.cotiinformatica.infrastructure.components;

import java.time.LocalTime;
import java.util.UUID;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cotiinformatica.domain.dtos.EmailDto;
import br.com.cotiinformatica.domain.entities.LogMensagem;
import br.com.cotiinformatica.infrastructure.repositories.LogMensagemRepository;

@Component
public class EmailConsumerComponent {

	@Autowired
	private EmailComponent emailComponent;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private LogMensagemRepository logMensagemRepository;
	
	/*
	 * Método para ficar processando o conteudo da fila
	 * enquanto o projeto estiver em execução
	 */
	@RabbitListener(queues = { "${queue.name}" })
	public void proccessMessage(@Payload String message) {
		
		LogMensagem logMensagem = new LogMensagem();
		logMensagem.setId(UUID.randomUUID());
		logMensagem.setMensagem(message);
		logMensagem.setDataHora(LocalTime.now());
		
		try {
			
			EmailDto dto = objectMapper.readValue(message, EmailDto.class);			
			emailComponent.send(dto);
			
			logMensagem.setStatus("SUCESSO");
		}
		catch(Exception e) {
			logMensagem.setStatus("ERRO");
			logMensagem.setErro(e.getMessage());
		}
		finally {
			//gravar o log no banco de dados
			logMensagemRepository.save(logMensagem);
		}
	}
}
