package aplicacao;

import java.util.List;

import model.dao.FabricaDeDAO;
import model.dao.VendedoresDao;
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
		
		

	}

}
