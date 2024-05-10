package br.com.cotiinformatica.application.controllers;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.domain.dtos.ConsultarTarefaResponseDto;
import br.com.cotiinformatica.domain.dtos.CriarTarefaRequestDto;
import br.com.cotiinformatica.domain.dtos.EditarTarefaRequestDto;
import br.com.cotiinformatica.domain.interfaces.TarefaDomainService;
import br.com.cotiinformatica.infrastructure.components.TokenComponent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/tarefas")
public class TarefaController {
	
	@Autowired
	private TarefaDomainService tarefaDomainService;
	
	@Autowired
	private TokenComponent tokenComponent;

	@PostMapping("criar")
	public ResponseEntity<ConsultarTarefaResponseDto> criar
		(@RequestBody @Valid CriarTarefaRequestDto dto, HttpServletRequest request) {
		
		ConsultarTarefaResponseDto response = tarefaDomainService.criar(dto, getPessoaId(request));
		return ResponseEntity.status(201).body(response);
	}

	@PutMapping("alterar")
	public ResponseEntity<ConsultarTarefaResponseDto> alterar
		(@RequestBody @Valid EditarTarefaRequestDto dto, HttpServletRequest request) {
		
		ConsultarTarefaResponseDto response = tarefaDomainService.editar(dto, getPessoaId(request));
		return ResponseEntity.status(200).body(response);
	}
	
	@DeleteMapping("excluir/{id}")
	public ResponseEntity<ConsultarTarefaResponseDto> excluir
		(@PathVariable("id") UUID id, HttpServletRequest request) {
		
		ConsultarTarefaResponseDto response = tarefaDomainService.excluir(id, getPessoaId(request));
		return ResponseEntity.status(200).body(response);
	}
	
	@GetMapping("consultar/{dataMin}/{dataMax}")
	public ResponseEntity<List<ConsultarTarefaResponseDto>> consultar
		(@PathVariable("dataMin") String dataMin, @PathVariable("dataMax") String dataMax, HttpServletRequest request) {
		
        // Converter string para Instant
        Instant dtMin = LocalDate.parse(dataMin).atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant dtMax = LocalDate.parse(dataMax).atStartOfDay(ZoneId.systemDefault()).toInstant();
		
		List<ConsultarTarefaResponseDto> response = tarefaDomainService.consultar(dtMin, dtMax, getPessoaId(request));
		return ResponseEntity.status(200).body(response);
	}
	
	@GetMapping("obter/{id}")
	public ResponseEntity<ConsultarTarefaResponseDto> obter
		(@PathVariable("id") UUID id, HttpServletRequest request) {
		
		ConsultarTarefaResponseDto response = tarefaDomainService.obter(id, getPessoaId(request));
		return ResponseEntity.status(200).body(response);
	}
	
	private UUID getPessoaId(HttpServletRequest request) {		
		String token = request.getHeader("Authorization").replace("Bearer", "").trim();
		return tokenComponent.getIdFromToken(token);
	}
}



