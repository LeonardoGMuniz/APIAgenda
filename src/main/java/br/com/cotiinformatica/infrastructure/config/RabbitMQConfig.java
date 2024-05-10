package br.com.cotiinformatica.infrastructure.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	/*
	 * Ler o nome da fila que iremos
	 * acessar no servidor
	 */
	@Value("${queue.name}")
	private String queueName;
	
	/*
	 * Método para conectar o projeto
	 * na fila do servidor do RabbitMQ
	 */
	@Bean
	public Queue queue() {
		return new Queue(queueName, true);
	}
}
