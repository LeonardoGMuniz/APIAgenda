package br.com.cotiinformatica.infrastructure.repositories;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.cotiinformatica.domain.entities.Tarefa;

@Repository
public interface TarefaRepository extends MongoRepository<Tarefa, UUID> {

	@Query("{ 'data' : { $gte : ?0, $lte : ?1 }, 'pessoa.id' : ?2 }")
	List<Tarefa> findByDatas(Instant dataMin, Instant dataMax, UUID pessoaId);
	
	@Query("{ 'id' : ?0, 'pessoa.id' : ?1 }")
	Tarefa findByIds(UUID tarefaId, UUID pessoaId);
}
