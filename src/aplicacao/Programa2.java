package aplicacao;

import java.util.List;
import java.util.Scanner;

import model.dao.DepartamentoDao;
import model.dao.FabricaDeDAO;
import model.entindade.Departamento;

public class Programa2 {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		DepartamentoDao departamentoDao = FabricaDeDAO.createDepartamentoDao();
		
		System.out.println("===Teste 1 - Pesquisa Departamento por ID===");
		System.out.print("Informe o ID do departamento: ");
		int id =sc.nextInt();
		Departamento departamento = departamentoDao.findById(id);
		System.out.println(departamento);
		
		
		
		System.out.println("\n===Teste 2 - Pesquisa retornando todos os Departamentos===");
		List<Departamento> listadepartamento = departamentoDao.findAll();
		for(Departamento departamentolista : listadepartamento) {
			System.out.println(departamentolista);
		}
		
		System.out.println("\n===Teste 3 - Inserindo Departamentos===");
		System.out.print("Informe o nome do novo Departamento: ");
		String dep = sc.next();
		Departamento novoDepartamento = new Departamento(null,dep);
		departamentoDao.insert(novoDepartamento);
		System.out.println("\n Inserido novo departamento - ID" + novoDepartamento.getId());
		
		System.out.println("\n===Teste 4 - Atualizando Departamentos===");
		System.out.print("Informe o ID do departamento para atualização: ");
		id =sc.nextInt();
		departamento = departamentoDao.findById(id);
		System.out.print("Informe o novo nome do Departamento: ");
		dep =sc.next();
		departamento.setNome(dep);
		departamentoDao.update(departamento);
		System.out.print("Departamento Atualizado!");
		
		
		System.out.println("\n===Teste 5 - Apagando Departamentos===");
		System.out.print("Informe o ID para apagar: ");
		id =sc.nextInt();
		departamentoDao.deleteById(id);
		System.out.print("Dados apagados!");
		
		
		sc.close();

	}

}
