package br.com.eduardo.spring.data.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.eduardo.spring.data.orm.Cargo;
import br.com.eduardo.spring.data.orm.Funcionario;
import br.com.eduardo.spring.data.orm.Unidade;
import br.com.eduardo.spring.data.repository.CargoRepository;
import br.com.eduardo.spring.data.repository.FuncionarioRepository;
import br.com.eduardo.spring.data.repository.UnidadeRepository;

@Service
public class CrudFuncionarioService {
	
	private Boolean system = true;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private final CargoRepository cargoRepository;
	private final FuncionarioRepository funcionarioRepository;
	private final UnidadeRepository unidadeRepository;
	
	public CrudFuncionarioService(FuncionarioRepository funcionarioRepository,
			CargoRepository cargoRepository,
			UnidadeRepository unidadeRepository) {
		this.funcionarioRepository = funcionarioRepository;
		this.cargoRepository = cargoRepository;
		this.unidadeRepository = unidadeRepository;
	}
	
	public void inicial(Scanner scanner) {
		while(system) {
			System.out.println("Qual ação de Funcionario deseja executar: ");
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
				visualizar(scanner);
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
		System.out.println("Nome do funcionario: ");
		String nome = scanner.next();
		
		System.out.println("CPF do funcionario: ");
		String cpf = scanner.next();
		
		System.out.println("Salário do funcionario: ");
		Double salario = scanner.nextDouble();
		
		System.out.println("Data de contratação do funcionario: ");
		String dataContratação = scanner.next();
		
		System.out.println("Id do cargo do funcionario: ");
		Integer cargoId = scanner.nextInt();
		
		List<Unidade> unidades = unidade(scanner);
		
		Funcionario funcionario = new Funcionario();
		funcionario.setNome(nome);
		funcionario.setCpf(cpf);
		funcionario.setSalario(salario);
		funcionario.setDataContratacao(LocalDate.parse(dataContratação, formatter));
		Optional<Cargo> cargo = cargoRepository.findById(cargoId);
		funcionario.setCargo(cargo.get());
		funcionario.setUnidadeTrabalhos(unidades);
		
		funcionarioRepository.save(funcionario);
		System.out.println("Salvo com sucesso!");
	}
	
	private List<Unidade> unidade(Scanner scanner){
		Boolean isTrue = true;
		List<Unidade> unidades = new ArrayList<>();
		
		while(isTrue) {
			System.out.println("Digite o Id da unidade ou 0 (zero) para sair: ");
			Integer unidadeId = scanner.nextInt();
			
			if(unidadeId != 0) {
				Optional<Unidade> unidade = unidadeRepository.findById(unidadeId);
				unidades.add(unidade.get());
			} else {
				isTrue = false;
			}
		}
		return unidades;
	}
	
	private void atualizar(Scanner scanner) {
		System.out.println("Id a ser atualizado: ");
		Integer id = scanner.nextInt();
		
		System.out.println("Nome do funcionario: ");
		String nome = scanner.next();
		
		System.out.println("CPF do funcionario: ");
		String cpf = scanner.next();
		
		System.out.println("Salário do funcionario: ");
		Double salario = scanner.nextDouble();
		
		System.out.println("Data de contratação do funcionario: ");
		String dataContratação = scanner.next();
		
		System.out.println("Id do cargo do funcionario: ");
		Integer cargoId = scanner.nextInt();
		
		Funcionario funcionario = new Funcionario();
		funcionario.setId(id);
		funcionario.setNome(nome);
		funcionario.setCpf(cpf);
		funcionario.setSalario(salario);
		funcionario.setDataContratacao(LocalDate.parse(dataContratação, formatter));
		Optional<Cargo> cargo = cargoRepository.findById(cargoId);
		funcionario.setCargo(cargo.get());
		
		funcionarioRepository.save(funcionario);
		System.out.println("Alterado com sucesso!");
	}
	
	private void visualizar(Scanner scanner) {
		//buscando por paginação
		System.out.println("Qual página você deseja visualizar? ");
		Integer page = scanner.nextInt();
		
		Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.ASC, "nome"));
		Page<Funcionario> funcionarios = funcionarioRepository.findAll(pageable);
		
		System.out.println(funcionarios);
		System.out.println("Página atual " + funcionarios.getNumber());
		System.out.println("Total elemento " + funcionarios.getTotalElements());
		funcionarios.forEach(funcionario -> System.out.print(funcionario));

	}
	
	private void deletar(Scanner scanner) {
		System.out.println("Id a ser deletado: ");
		int id = scanner.nextInt();
		funcionarioRepository.deleteById(id);
		System.out.println("Deletado!");
	}
}
