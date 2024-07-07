package model.dao;

import java.util.List;

import model.entindade.Vendedores;

public interface VendedoresDao {
	
	//Metodo para inserir um Vendedores no banco de dados
		void insert (Vendedores obj);
		
		//Metodo para atualizar um Vendedores no banco de dados
		void update (Vendedores obj);
		
		//Metodo para deletar um Vendedores no banco de dados, através do ID
		void deleteById(Integer id);
		
		//Metodo para pesquisar um Vendedores no banco de dados pelo ID
		Vendedores findById(Integer id);
		
		//Metodo para retornar todos os Vendedoress do banco de dados
		//É criado uma lista com todos os Vendedores
		List<Vendedores> findAll();
	

}
