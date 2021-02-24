package com.example.unidac.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.unidac.api.model.Colaborador;

@Repository
public interface ColaboradorRepository extends CrudRepository<Colaborador, Long>, PagingAndSortingRepository<Colaborador, Long>{
	
	//Salvar colaborador
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO colaborador (nome, cpf) VALUES (:nome, :cpf)", nativeQuery = true)
	void salvar(@Param("nome") String nome, @Param("cpf") String cpf);
	
	//Atualizar colaborador
	@Transactional
	@Modifying
	@Query(value = "UPDATE colaborador SET (nome= :nome, cpf= :cpf) WHERE (colaborador_codigo= :codigo)", nativeQuery = true)
	void atualizar(@Param("nome") String nome, @Param("cpf") String cpf, Long codigo);
	
	//Listar todos os colaboradores
	@Query(value = "SELECT * FROM colaborador", nativeQuery = true)
	List<Colaborador> listarTodos();
	
	//Listar colaboradores pelo código
	@Query(value = "SELECT * FROM colaborador c WHERE c.colaborador_codigo= :codigo", nativeQuery = true)
	List<Colaborador> listarPorCodigo(Long codigo);
	
	//Deletar colaborador pelo código
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM colaborador c WHERE c.colaborador_codigo= :codigo", nativeQuery = true)
	void deletarPorCodigo(Long codigo);
	
	//Deletar opções de um colaborador
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM opcao o WHERE o.colaborador_codigo= :codigo", nativeQuery = true)
	void deletarOpcoesPorCodigo(Long codigo);
	
}
