package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Vendedores obj) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
