package br.com.eduardo.spring.data.service;

import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.eduardo.spring.data.orm.Unidade;
import br.com.eduardo.spring.data.repository.UnidadeRepository;

@Service
public class CrudUnidadeService {
	
	private Boolean system = true;
	private final UnidadeRepository unidadeRepository;
	
	public CrudUnidadeService(UnidadeRepository unidadeRepository) {
		this.unidadeRepository = unidadeRepository;
	}
	
	public void inicial(Scanner scanner) {
		while(system) {
			System.out.println("Qual ação de Unidade deseja executar: ");
			System.out.println("0 - Sair");
			System.out.println("1 - Cadastrar");
			System.out.println("2 - Atualizar");
			System.out.println("3 - Visualizar");
			System.out.println("4 - Deletar");
			
			int action = scanner.nextInt();
			switch (action) {
			case 1:
				salvar(scanner);
				break;
			case 2:
				atualizar(scanner);
				break;
			case 3:
				visualizar();
				break;
			case 4:
				deletar(scanner);
				break;
			default:
				system = false;
				break;
			}
		}
		
	}
	
	private void salvar(Scanner scanner) {
		System.out.println("Descrição da unidade: ");
		String descricao = scanner.next();
		
		System.out.println("Endereço da unidade: ");
		String endereco = scanner.next();
		
		Unidade unidade = new Unidade();
		unidade.setDescricao(descricao);
		unidade.setEndereco(endereco);
		
		unidadeRepository.save(unidade);
		System.out.println("Salvo com sucesso!");
	}
	
	private void atualizar(Scanner scanner) {
		System.out.println("Id a ser atualizado: ");
		Integer id = scanner.nextInt();
		
		System.out.println("Descrição da unidade: ");
		String descricao = scanner.next();
		
		System.out.println("Endereço da unidade: ");
		String endereco = scanner.next();
		
		Unidade unidade = new Unidade();
		unidade.setId(id);
		unidade.setDescricao(descricao);
		unidade.setEndereco(endereco);
		
		unidadeRepository.save(unidade);
		System.out.println("Alterado com sucesso!");
	}
	
	private void visualizar() {
		Iterable<Unidade> cargos = unidadeRepository.findAll();
		cargos.forEach(cargo -> System.out.print(cargo));
	}
	
	private void deletar(Scanner scanner) {
		System.out.println("Id a ser deletado: ");
		int id = scanner.nextInt();
		unidadeRepository.deleteById(id);
		System.out.println("Deletado!");
	}
}
