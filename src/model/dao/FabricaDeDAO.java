package model.dao;

import model.dao.impl.VendedoresDaoJDBCImpl;

public class FabricaDeDAO {
	
	public static VendedoresDao createVendedoresDao() {
		return new VendedoresDaoJDBCImpl();
	}

}
