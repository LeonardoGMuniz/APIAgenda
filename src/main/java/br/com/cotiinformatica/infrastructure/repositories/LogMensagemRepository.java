package br.com.cotiinformatica.infrastructure.repositories;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.cotiinformatica.domain.entities.LogMensagem;

@Repository
public interface LogMensagemRepository extends MongoRepository<LogMensagem, UUID> {

}
