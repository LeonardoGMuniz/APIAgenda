package br.com.cotiinformatica.domain.services;

import java.util.Date;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cotiinformatica.domain.dtos.AutenticarPessoaRequestDto;
import br.com.cotiinformatica.domain.dtos.AutenticarPessoaResponseDto;
import br.com.cotiinformatica.domain.dtos.CriarPessoaRequestDto;
import br.com.cotiinformatica.domain.dtos.CriarPessoaResponseDto;
import br.com.cotiinformatica.domain.dtos.EmailDto;
import br.com.cotiinformatica.domain.entities.Pessoa;
import br.com.cotiinformatica.domain.exceptions.AcessoNegadoException;
import br.com.cotiinformatica.domain.exceptions.EmailJaCadastradoException;
import br.com.cotiinformatica.domain.interfaces.PessoaDomainService;
import br.com.cotiinformatica.infrastructure.components.CryptoSHA256Component;
import br.com.cotiinformatica.infrastructure.components.EmailProducerComponent;
import br.com.cotiinformatica.infrastructure.components.TokenComponent;
import br.com.cotiinformatica.infrastructure.repositories.PessoaRepository;

@Service
public class PessoaDomainServiceImpl implements PessoaDomainService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private CryptoSHA256Component cryptoSHA256Component;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private EmailProducerComponent emailProducerComponent;
	
	@Autowired
	private TokenComponent tokenComponent;
	
	@Override
	public CriarPessoaResponseDto criar(CriarPessoaRequestDto dto) {

		//REGRA: Não permitir email duplicado
		if(pessoaRepository.findByEmail(dto.getEmail()) != null)
			throw new EmailJaCadastradoException();
		
		//Preencher os dados da pessoa
		Pessoa pessoa = new Pessoa();
		
		pessoa.setId(UUID.randomUUID());
		pessoa.setNome(dto.getNome());
		pessoa.setEmail(dto.getEmail());
		pessoa.setSenha(cryptoSHA256Component.encrypt(dto.getSenha()));
		
		//Gravar no banco de dados
		pessoaRepository.save(pessoa);
		
		//Enviando o email de boas vindas
		enviarEmailDeBoasVindas(pessoa);
			
		//Copiar os dados de pessoa para o objeto 'CriarPessoaResponseDto'
		CriarPessoaResponseDto response = modelMapper.map(pessoa, CriarPessoaResponseDto.class);
		response.setDataHoraCadastro(new Date());
		
		return response; //retornando os dados
	}
	
	@Override
	public AutenticarPessoaResponseDto autenticar(AutenticarPessoaRequestDto dto) {

		//buscar o registro da pessoa no banco de dados através do email e da senha
		Pessoa pessoa = pessoaRepository.findByEmailAndSenha
				(dto.getEmailAcesso(), cryptoSHA256Component.encrypt(dto.getSenhaAcesso()));
		
		//verificar se pessoa não foi encontrado
		if(pessoa == null)
			throw new AcessoNegadoException();
		
		//copiando os dados de pessoa para a classe response
		AutenticarPessoaResponseDto response = modelMapper.map(pessoa, AutenticarPessoaResponseDto.class);
		response.setAccessToken(tokenComponent.generateToken(pessoa.getId()));
				
		return response;
	}
	
	/*
	 * Método para escrever o email de boas vindas da pessoa
	 */
	private void enviarEmailDeBoasVindas(Pessoa pessoa) {
		
		String to = pessoa.getEmail();
		String subject = "Seja bem vindo ao sistema de Agenda - COTI Informática.";
		String body = "Olá, " + pessoa.getNome()
				    + "\nSua conta foi criada com sucesso no sistema de Agenda de tarefas"
				    + "\nSeja bem vindo!"
				    + "\n\nAtt, "
				    + "\nEquipe COTI Informática";
		
		EmailDto dto = new EmailDto();
		dto.setDestinatario(to);
		dto.setAssunto(subject);
		dto.setMensagem(body);
		
		try {
			emailProducerComponent.sendMessage(dto);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}




