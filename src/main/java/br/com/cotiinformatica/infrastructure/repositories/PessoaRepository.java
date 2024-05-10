package br.com.cotiinformatica.infrastructure.repositories;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.cotiinformatica.domain.entities.Pessoa;

@Repository
public interface PessoaRepository extends MongoRepository<Pessoa, UUID> {

	/*
	 * Método para consultar 1 pessoa no banco de dados
	 * do MongoDB através do email informado
	 */
	@Query("{ 'email' : ?0 }")
	Pessoa findByEmail(String email);
	
	/*
	 * Método para consultar 1 pessoa no banco de dados
	 * do MongoDB através do email e da senha informada
	 */
	@Query("{ 'email' : ?0, 'senha' : ?1 }")
	Pessoa findByEmailAndSenha(String email, String senha);
}
