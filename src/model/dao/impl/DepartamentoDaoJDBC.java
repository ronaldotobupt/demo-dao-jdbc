package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.cj.protocol.a.SqlTimeValueEncoder;

import db.DB;
import db.DbException;
import model.dao.DepartamentoDao;
import model.entindade.Departamento;

public class DepartamentoDaoJDBC implements DepartamentoDao {
	
	private Connection conn;
	public DepartamentoDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Departamento obj) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"INSERT INTO department "
					+
					"(Name)"
					+
					"VALUES "
					+
					"(?) ",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setNString(1, obj.getNome());
			
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
	public void update(Departamento obj) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"UPDATE department "
					+
					"SET Name=?"
					+
					"WHERE Id = ?");
			
			st.setString(1, obj.getNome());
			st.setInt(2, obj.getId());
			
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
		
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"DELETE FROM department "
					+
					"WHERE Id = ?");
			
			st.setInt(1, id);
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
	public Departamento findById(Integer id) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			
			st = conn.prepareStatement(
					"SELECT department.* FROM department "
					+
					"WHERE department.Id = ? ");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if(rs.next()) {
				Departamento dep = instanciarDepartamento(rs);
				return dep;
			}
			else {
				return null;
			}
			
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
	public List<Departamento> findAll() {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT department.* FROM department "
					+
					"ORDER BY Name ");
			
			rs = st.executeQuery();
			
			List<Departamento> listadepartamento = new ArrayList<>();
			
			Map<Integer, Departamento> mapDepartamento = new HashMap<>();
			
			while (rs.next()) {
				Departamento dep = mapDepartamento.get(rs.getInt("department.Id"));
				
				if(dep == null) {
					dep = instanciarDepartamento(rs);
					mapDepartamento.put(rs.getInt("department.Id"), dep);
				}
				
				listadepartamento.add(dep);
			}
			return listadepartamento;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
		
	}
	
	
	private Departamento instanciarDepartamento(ResultSet rs) throws SQLException {
		Departamento dep = new Departamento();
		dep.setId(rs.getInt("Department.Id"));
		dep.setNome(rs.getString("Name"));
		return dep;
	}

}
