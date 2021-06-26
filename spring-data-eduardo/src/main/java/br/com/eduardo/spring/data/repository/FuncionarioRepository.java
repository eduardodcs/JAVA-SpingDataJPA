package br.com.eduardo.spring.data.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.eduardo.spring.data.orm.Funcionario;
import br.com.eduardo.spring.data.orm.FuncionarioProjecao;

//CrudRepository - Faz a busca direto com todos os resultados
//PagingAndSortingRepository - Faz a busca por paginação

@Repository
public interface FuncionarioRepository extends PagingAndSortingRepository<Funcionario, Integer>,
	JpaSpecificationExecutor<Funcionario>{
	//usando o spring data - Derived Query
	List<Funcionario> findByNome(String nome);
	
	//usando jpdl
	@Query("SELECT f FROM Funcionario f WHERE f.nome = :nome AND f.salario >= :salario "
			+ "AND f.dataContratacao = :data")
	List<Funcionario> findNomeSalarioMaiorDataContratacao(String nome, Double salario, LocalDate data);

	//usando Native Query
	@Query(value = "SELECT * FROM funcionarios f WHERE f.data_contratacao >= :data",
			nativeQuery = true)
	List<Funcionario> findDataContratacaoMaior(LocalDate data);
	
	@Query(value = "SELECT f.id , f.nome, f.salario FROM funcionarios f",
			nativeQuery = true)
	List<FuncionarioProjecao> findFuncionarioSalario();
	
}
