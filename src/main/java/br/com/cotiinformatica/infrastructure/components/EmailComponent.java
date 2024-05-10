package br.com.cotiinformatica.infrastructure.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import br.com.cotiinformatica.domain.dtos.EmailDto;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailComponent {

	/*
	 * Componente do Spring Mail para realizar envio de emails 
	 * (usando as configurações do /application.properties)
	 */
	@Autowired
	private JavaMailSender javaMailSender;
	
	/*
	 * Lendo o valor da configuração 'spring.mail.username'
	 * contida no arquivo /application.properties
	 */
	@Value("${spring.mail.username}")
	private String userName;
	
	/*
	 * Criar o método para fazer o envio do email
	 */
	public void send(EmailDto dto) throws Exception {
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		
		helper.setFrom(userName); //remetente da mensagem
		helper.setTo(dto.getDestinatario()); //destinatário da mensagem
		helper.setSubject(dto.getAssunto()); //assunto
		helper.setText(dto.getMensagem()); //texto da mensagem
		
		javaMailSender.send(mimeMessage); //enviando a mensagem
	}
}




