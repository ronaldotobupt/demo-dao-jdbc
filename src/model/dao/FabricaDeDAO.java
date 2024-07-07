package model.dao;

import db.DB;
import model.dao.impl.VendedoresDaoJDBCImpl;

public class FabricaDeDAO {
	
	public static VendedoresDao createVendedoresDao() {
		return new VendedoresDaoJDBCImpl(DB.getConnection());
	}

}
