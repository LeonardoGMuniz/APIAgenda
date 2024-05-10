package br.com.cotiinformatica.infrastructure.components;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cotiinformatica.domain.dtos.EmailDto;

@Component
public class EmailProducerComponent {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private Queue queue;
	
	/*
	 * Método para receber um objeto DTO contendo uma mensagem de email
	 * e gravar este objeto em uma fila do RabbitMQ
	 */
	public void sendMessage(EmailDto dto) throws Exception {
		
		//serializando os dados do dto para formato JSON
		String data = objectMapper.writeValueAsString(dto);
		
		//gravando a mensagen na fila
		rabbitTemplate.convertAndSend(queue.getName(), data);
	}
}
