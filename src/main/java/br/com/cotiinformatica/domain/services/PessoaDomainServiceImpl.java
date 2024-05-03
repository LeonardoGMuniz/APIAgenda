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
	private ModelMapper modelMapper;

	@Autowired
	private EmailProducerComponent emailProducerComponent;
	
	@Autowired
	private TokenComponent tokenComponent;

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private CryptoSHA256Component cryptoSHA256Component;

	@Override
	public CriarPessoaResponseDto criar(CriarPessoaRequestDto dto) {
		
		
		

		if (pessoaRepository.findByEmail(dto.getEmail()) != null)
			throw new EmailJaCadastradoException();
		Pessoa pessoa = new Pessoa();

		pessoa.setId(UUID.randomUUID());
		pessoa.setEmail(dto.getEmail());
		pessoa.setNome(dto.getNome());
		pessoa.setSenha(cryptoSHA256Component.encrypt(dto.getSenha()));
		// TODO Auto-generated method stub

		pessoaRepository.save(pessoa);

		enviarEmailDeBoasVindas(pessoa);

		CriarPessoaResponseDto response = modelMapper.map(pessoa, CriarPessoaResponseDto.class);
		response.setDataHoraCadastro(new Date());

		return response;
	}
	
	
	
	
	

	@Override
	public AutenticarPessoaResponseDto autenticar (AutenticarPessoaRequestDto dto) {
		
		Pessoa pessoa = pessoaRepository.findByEmailAndSenha(dto.getEmailAcesso(),cryptoSHA256Component.encrypt(dto.getSenhaAcesso()));
		 if(pessoa==null)
			 throw new AcessoNegadoException();
		 
		 AutenticarPessoaResponseDto response = modelMapper.map(pessoa,AutenticarPessoaResponseDto.class);
		 response.setAccessToken(tokenComponent.generateToken(pessoa.getId()));
		
		// TODO Auto-generated method stub
		return response;

	}

	private void enviarEmailDeBoasVindas(Pessoa pessoa) {

		String to = pessoa.getEmail();
		String subject = "Seja Bem vinda ao Sistema de Agenda -Coti Informática.";
		String body = "Olá," + pessoa.getNome() + "\nSua conta foi criada com sucesso no sistema de Agenda de tarefas"
				+ "\nSeja bem vindo!" + "\n\nAtt," + "\nEquipe Coti Informática";
		EmailDto dto = new EmailDto();
		dto.setDestionatario(to);
		dto.setAssunto(to);
		dto.setMensagem(body);

		try {

			emailProducerComponent.sendMessage(dto);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
