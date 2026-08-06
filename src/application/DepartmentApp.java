package application;

import java.util.ArrayList;
import java.util.List;

import model.dao.DAOFactory;
import model.dao.DepartmentDAO;
import model.entities.Department;

public class DepartmentApp {

	public static void main(String[] args) {

		System.out.println("----------------------TEST 1: FIND BY ID----------------------");

		DepartmentDAO departmentDAO = DAOFactory.createDepartmentDAO();

		Department department = departmentDAO.findById(1);
		System.out.println(department);

		System.out.println();
		System.out.println("----------------------TEST 2: FIND ALL----------------------");

		List<Department> departments = departmentDAO.findAll();

		for (Department dep : departments) {
			System.out.println(dep);
		}

		System.out.println();
		System.out.println("----------------------TEST 3: INSERT----------------------");

		department.setId(null);
		department.setName("Toys");

		departmentDAO.insert(department);

		System.out.println(department);

		System.out.println("Inserted");
		System.out.println();

		System.out.println("----------------------TEST 4: UPDATE----------------------");

		department.setId(1);
		department.setName("UPDATED");

		departmentDAO.update(department);

		System.out.println("Updated");
		System.out.println();

		System.out.println("----------------------TEST 5: DELETE BY ID----------------------");
	
		departmentDAO.deleteById(7);
		System.out.println("Deleted");

	}

}
