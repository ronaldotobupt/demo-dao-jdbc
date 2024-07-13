package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.cj.xdevapi.Statement;

import db.DB;
import db.DbException;
import model.dao.VendedoresDao;
import model.entindade.Departamento;
import model.entindade.Vendedores;

public class VendedoresDaoJDBCImpl implements VendedoresDao {
	
	//Criando a conexão com banco de dados
	
	private Connection conn;
	public VendedoresDaoJDBCImpl(Connection conn) {
		this.conn = conn;
	}
	
	
	
	@Override
	public void insert(Vendedores obj) {
		
		//Meteodo para inserir dados
		
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO seller "
					+
					"(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+
					"VALUES "
					+
					"(?, ?, ?, ?, ?) " ,
					java.sql.Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getNome());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getDataAniversario().getTime()));
			st.setDouble(4, obj.getSalarioBase());
			st.setInt(5, obj.getDepartamento().getId());
			
			int linhasinseridas = st.executeUpdate();
			
			if(linhasinseridas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Erro inesperado! Nenhum cadastro relizado");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void update(Vendedores obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE seller "
					+
					"SET Name=?, Email=?, BirthDate=?, BaseSalary=?, DepartmentId=? "
					+
					"WHERE Id = ? ");
			
			st.setString(1, obj.getNome());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getDataAniversario().getTime()));
			st.setDouble(4, obj.getSalarioBase());
			st.setInt(5, obj.getDepartamento().getId());
			st.setInt(6, obj.getId());
			
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Vendedores findById(Integer id) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+
					"FROM seller INNER JOIN department "
					+
					"ON seller.DepartmentId = department.Id "
					+
					"WHERE seller.Id=? ");
			st.setInt(1, id);
			rs = st.executeQuery();
			
			//Criando um objeto com os dados consultados no banco
			if(rs.next())//Testando se houve resultado 
			{
				Departamento dep = instanciarDepartamento(rs);
				Vendedores obj = instanciarVendedores(rs,dep);
				return obj;
			}
			return null; // Retornando null se não encontrar nenhum vendedor
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
	}

	private Vendedores instanciarVendedores(ResultSet rs, Departamento dep) throws SQLException {
		Vendedores obj = new Vendedores();
		obj.setId(rs.getInt("Id"));
		obj.setNome(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setSalarioBase(rs.getDouble("BaseSalary"));
		obj.setDataAniversario(rs.getDate("BirthDate"));
		obj.setDepartamento(dep);
		return obj;
	}



	private Departamento instanciarDepartamento(ResultSet rs) throws SQLException {
		Departamento dep = new Departamento();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setNome(rs.getString("DepName"));
		return dep;
	}



	@Override
	public List<Vendedores> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+
					"FROM seller INNER JOIN department "
					+
					"ON seller.DepartmentId = department.Id "
					+
					"Order By Name");
			
			rs = st.executeQuery();
			
			//Criando uma lista para armazenar os vendedores por departamento
			List<Vendedores> listavendedores = new ArrayList<>();
			
			//Solução para criar uma lista com 1 departamento com vários vendedores
			
			//Usando comando Map para controlar a instanciação de departamento
			Map<Integer, Departamento> mapdepartamento = new HashMap<>();
			
			//Criando um objeto com os dados consultados no banco
			while(rs.next())//Percorrendo o result set enquanto houver valor 
			{
				
				//Testando se o dapartamento já foi instanciado
				Departamento dep = mapdepartamento.get(rs.getInt("DepartmentId"));//Se o departamento não existir irá retornar null
				
				//Instanciando o departamento caso ainda não exista
				if(dep == null) 
					{
						dep = instanciarDepartamento(rs);//Intanciando o departamento
						mapdepartamento.put(rs.getInt("DepartmentId"), dep);//Salvando o departamento instanciado na estrura map, no próximo while irá identificar que já existe
					}
				Vendedores obj = instanciarVendedores(rs,dep);
				listavendedores.add(obj);//Adicionando os vendedores na lista
			}
			return listavendedores; // Retornando a lista de vendedores por departamento
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}



	@Override
	public List<Vendedores> findAll(Departamento departamento) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+
					"FROM seller INNER JOIN department "
					+
					"ON seller.DepartmentId = department.Id "
					+
					"WHERE DepartmentId=? "
					+
					"Order By Name");
			st.setInt(1, departamento.getId());
			rs = st.executeQuery();
			
			//Criando uma lista para armazenar os vendedores por departamento
			List<Vendedores> listavendedores = new ArrayList<>();
			
			//Solução para criar uma lista com 1 departamento com vários vendedores
			
			//Usando comando Map para controlar a instanciação de departamento
			Map<Integer, Departamento> mapdepartamento = new HashMap<>();
			
			//Criando um objeto com os dados consultados no banco
			while(rs.next())//Percorrendo o result set enquanto houver valor 
			{
				
				//Testando se o dapartamento já foi instanciado
				Departamento dep = mapdepartamento.get(rs.getInt("DepartmentId"));//Se o departamento não existir irá retornar null
				
				//Instanciando o departamento caso ainda não exista
				if(dep == null) 
					{
						dep = instanciarDepartamento(rs);//Intanciando o departamento
						mapdepartamento.put(rs.getInt("DepartmentId"), dep);//Salvando o departamento instanciado na estrura map, no próximo while irá identificar que já existe
					}
				Vendedores obj = instanciarVendedores(rs,dep);
				listavendedores.add(obj);//Adicionando os vendedores na lista
			}
			return listavendedores; // Retornando a lista de vendedores por departamento
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
	}
	
	

}
