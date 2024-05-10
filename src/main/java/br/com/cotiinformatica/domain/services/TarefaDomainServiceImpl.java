package br.com.cotiinformatica.domain.services;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cotiinformatica.domain.dtos.ConsultarTarefaResponseDto;
import br.com.cotiinformatica.domain.dtos.CriarTarefaRequestDto;
import br.com.cotiinformatica.domain.dtos.EditarTarefaRequestDto;
import br.com.cotiinformatica.domain.entities.Pessoa;
import br.com.cotiinformatica.domain.entities.Tarefa;
import br.com.cotiinformatica.domain.interfaces.TarefaDomainService;
import br.com.cotiinformatica.infrastructure.repositories.PessoaRepository;
import br.com.cotiinformatica.infrastructure.repositories.TarefaRepository;

@Service
public class TarefaDomainServiceImpl implements TarefaDomainService {

	@Autowired 
	private TarefaRepository tarefaRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public ConsultarTarefaResponseDto criar(CriarTarefaRequestDto dto, UUID pessoaId) {

		//capturar os dados da pessoa autenticada
		Pessoa pessoa = pessoaRepository.findById(pessoaId).get();
		
		//copiar os dados do dto para a 'tarefa'
		Tarefa tarefa = modelMapper.map(dto, Tarefa.class);
		tarefa.setId(UUID.randomUUID()); //gerando o id da tarefa
		tarefa.setPessoa(pessoa); //relacionando a pessoa da tarefa
		
		//gravando no banco de dados
		tarefaRepository.save(tarefa);
		
		//copiar os dados da tarefa para o dto de resposta
		return modelMapper.map(tarefa, ConsultarTarefaResponseDto.class);
	}

	@Override
	public ConsultarTarefaResponseDto editar(EditarTarefaRequestDto dto, UUID pessoaId) {

		//capturar os dados da pessoa autenticada
		Pessoa pessoa = pessoaRepository.findById(pessoaId).get();
		
		//copiar os dados do dto para a 'tarefa'
		Tarefa tarefa = modelMapper.map(dto, Tarefa.class);
		tarefa.setPessoa(pessoa); //relacionando a pessoa da tarefa
		
		//atualizando no banco de dados
		tarefaRepository.save(tarefa);
		
		//copiar os dados da tarefa para o dto de resposta
		return modelMapper.map(tarefa, ConsultarTarefaResponseDto.class);
	}

	@Override
	public ConsultarTarefaResponseDto excluir(UUID tarefaId, UUID pessoaId) {

		//consultar a tarefa no banco de dados através dos ids
		Tarefa tarefa = tarefaRepository.findByIds(tarefaId, pessoaId);
		
		//excluindo a tarefa no banco de dados
		tarefaRepository.delete(tarefa);
		
		//copiar os dados da tarefa para o dto de resposta
		return modelMapper.map(tarefa, ConsultarTarefaResponseDto.class);
	}

	@Override
	public List<ConsultarTarefaResponseDto> consultar(Instant dataMin, Instant dataMax, UUID pessoaId) {

		//consultar as tarefas por datas e pessoa
		List<Tarefa> tarefas = tarefaRepository.findByDatas(dataMin, dataMax, pessoaId);
		
		//copiar os dados da lista para uma outra lista da classe DTO
		return tarefas
				.stream()
				.map(tarefa -> modelMapper.map(tarefa, ConsultarTarefaResponseDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public ConsultarTarefaResponseDto obter(UUID tarefaId, UUID pessoaId) {

		//consultar a tarefa no banco de dados através dos ids
		Tarefa tarefa = tarefaRepository.findByIds(tarefaId, pessoaId);
		
		//copiar os dados da tarefa para o dto de resposta
		return modelMapper.map(tarefa, ConsultarTarefaResponseDto.class);
	}

}
