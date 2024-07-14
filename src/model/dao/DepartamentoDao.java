package model.dao;

import java.util.List;

import model.entindade.Departamento;

public interface DepartamentoDao {
	
	//Metodo para inserir um Departamento no banco de dados
	void insert (Departamento obj);
	
	//Metodo para atualizar um Departamento no banco de dados
	void update (Departamento obj);
	
	//Metodo para deletar um Departamento no banco de dados, através do ID
	void deleteById(Integer id);
	
	//Metodo para pesquisar um Departamento no banco de dados pelo ID
	Departamento findById(Integer id);
	
	//Metodo para retornar todos os Departamentos do banco de dados
	//É criado uma lista com todos os departamentos
	List<Departamento> findAll();
	
	

}
