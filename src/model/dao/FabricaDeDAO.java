package model.dao;

import db.DB;
import model.dao.impl.DepartamentoDaoJDBC;
import model.dao.impl.VendedoresDaoJDBCImpl;

public class FabricaDeDAO {
	
	public static VendedoresDao createVendedoresDao() {
		return new VendedoresDaoJDBCImpl(DB.getConnection());
	}
	
	public static DepartamentoDao createDepartamentoDao() {
		return new DepartamentoDaoJDBC(DB.getConnection());
	}

}
