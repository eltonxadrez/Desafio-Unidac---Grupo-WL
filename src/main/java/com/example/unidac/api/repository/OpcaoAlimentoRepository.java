package com.example.unidac.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.unidac.api.model.OpcaoAlimento;

public interface OpcaoAlimentoRepository extends CrudRepository<OpcaoAlimento, Long>, PagingAndSortingRepository<OpcaoAlimento, Long> {
	
	//Salvar uma opção de alimento
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO opcao (nome, colaborador_codigo) VALUES (:nome, :colaborador_codigo)", nativeQuery = true)
	void salvar(@Param("nome") String nome, @Param("colaborador_codigo") Long colaboradorCodigo);
	
	//Atualizar as opções de alimento
	@Transactional
	@Modifying
	@Query(value = "UPDATE opcao SET nome= :nome WHERE opcao_codigo= :codigo", nativeQuery = true)
	void atualizar(@Param("nome") String nome, Long codigo);
	
	//Listar todas as opções de alimento
	@Query(value = "SELECT * FROM opcao", nativeQuery = true)
	List<OpcaoAlimento> listarTodos();
	
	//Listar opções de alimento pelo código
	@Query(value = "SELECT * FROM opcao o WHERE o.opcao_codigo= :codigo", nativeQuery = true)
	List<OpcaoAlimento> listarPorCodigo(Long codigo);
	
	//Deletar opções de alimento pelo código
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM opcao o WHERE o.opcao_codigo= :codigo", nativeQuery = true)
	void deletarPorCodigo(Long codigo);
	
	
}
