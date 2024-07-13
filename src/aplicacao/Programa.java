package aplicacao;

import java.util.Date;
import java.util.List;

import model.dao.FabricaDeDAO;
import model.dao.VendedoresDao;
import model.dao.impl.VendedoresDaoJDBCImpl;
import model.entindade.Departamento;
import model.entindade.Vendedores;

public class Programa {

	public static void main(String[] args) {
		
		VendedoresDao vendedoresDao = FabricaDeDAO.createVendedoresDao();
		
		System.out.println("===Teste 1 - Pesquisa Vendedores por ID===");
		Vendedores vendedores = vendedoresDao.findById(3);
		System.out.println(vendedores);
		
		System.out.println("\n===Teste 2 - Pesquisa Vendedores por Departamento===");
		Departamento departamento = new Departamento(2,null);
		List<Vendedores> listavendedores = vendedoresDao.findAll(departamento);
		for(Vendedores vendedoreslista : listavendedores) {
			System.out.println(vendedoreslista);
		}
		
		
		System.out.println("\n===Teste 3 - Pesquisa retornando todos os Vendedores===");
		listavendedores = vendedoresDao.findAll();
		for(Vendedores vendedoreslista : listavendedores) {
			System.out.println(vendedoreslista);
		}
		
		System.out.println("\n===Teste 4 - Inserindo Vendedores===");
		Vendedores novoVendedor = new Vendedores(null,"Greg","greg@gmail.com",new Date(), 4000.0, departamento);
		vendedoresDao.insert(novoVendedor);
		System.out.println("\n Inserido! Novo id: " + novoVendedor.getId());

		System.out.println("\n===Teste 5 - Atualizando Vendedores===");
		vendedores = vendedoresDao.findById(1);
		vendedores.setNome("Matha Waine");
		vendedoresDao.update(vendedores);
		System.out.println("Atualização completa!");
		
	}

}
