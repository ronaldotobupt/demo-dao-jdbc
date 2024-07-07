package aplicacao;

import java.util.Date;

import model.dao.FabricaDeDAO;
import model.dao.VendedoresDao;
import model.entindade.Departamento;
import model.entindade.Vendedores;

public class Programa {

	public static void main(String[] args) {
		
		Departamento obj = new Departamento(1,"Livros");
		Vendedores vendedores = new Vendedores(21,"Bob","bob@gmail.com",new Date(),3000.0,obj);
		
		VendedoresDao vendedoresDao = FabricaDeDAO.createVendedoresDao();
		
		System.out.println(vendedores);

	}

}
