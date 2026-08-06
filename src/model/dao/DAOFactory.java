package model.dao;

import model.dao.implments.DepartmentDAOJDBC;
import model.dao.implments.SellerDAOJDBC;
import services.DBServices;

public class DAOFactory {

	public static SellerDAO createSellerDAO() {
		return new SellerDAOJDBC(DBServices.getConnection());
	}

	public static DepartmentDAO createDepartmentDAO() {
		return new DepartmentDAOJDBC(DBServices.getConnection());
	}
	
}
