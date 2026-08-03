package application;

import model.dao.DAOFactory;
import model.dao.DepartmentDAO;
import model.entities.Department;

public class Program {

	public static void main(String[] args) {

		DepartmentDAO departmentDao = DAOFactory.createDepartmentDAO();

		Department dep = departmentDao.findById(3);

		System.out.println(dep);

	}

}
