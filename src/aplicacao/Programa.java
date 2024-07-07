package aplicacao;

import java.util.Date;

import model.entindade.Departamento;
import model.entindade.Vendedores;

public class Programa {

	public static void main(String[] args) {
		
		Departamento obj = new Departamento(1,"Livros");
		Vendedores vendedores = new Vendedores(21,"Bob","bob@gmail.com",new Date(),3000.0,obj);
		System.out.println(vendedores);

	}

}
