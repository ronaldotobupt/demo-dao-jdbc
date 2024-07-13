package aplicacao;

import java.util.Date;

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

	}

}
