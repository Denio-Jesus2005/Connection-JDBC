package model.dao;

import model.dao.implments.DepartmentDAOJDBC;
import model.dao.implments.SellerDAOJDBC;

public class DAOFactory {

	public static SellerDAO createSellerDAO() {

		return new SellerDAOJDBC();
	}

	public static DepartmentDAO createDepartmentDAO() {
		return new DepartmentDAOJDBC();
	}
	
}
