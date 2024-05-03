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
	
	
	public void sendMessage(EmailDto dto) throws Exception{
		
		String data = objectMapper.writeValueAsString(dto);
		
		rabbitTemplate.convertAndSend(queue.getName(),data);
		
		
		
}
	
	

}
