package application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import model.dao.DAOFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		System.out.println("----------------------TEST 1: FIND BY ID----------------------");

		SellerDAO sellerDao = DAOFactory.createSellerDAO();

		Seller sel = sellerDao.findById(1);
		System.out.println(sel);
		System.out.println();
		
		
		System.out.println("----------------------TEST 2: FIND BY DEPARTMENT----------------------");

		Department dep = new Department(1, null);

		List<Seller> sellers = sellerDao.findByDepartment(dep);
		for (Seller seller : sellers) {
			System.out.println(seller);
		}
		System.out.println();

		System.out.println("----------------------TEST 3: FIND ALL----------------------");

		sellers = sellerDao.findAll();

		for (Seller seller : sellers) {
			System.out.println(seller);
		}
		
		System.out.println("----------------------TEST 4: INSERT----------------------");

		Department department = new Department(1, "Computaria");
		sel = new Seller(null, "Denio Jesus", "denios", LocalDate.parse("07/11/2005", fmt), 3.200, department);
		
		sellerDao.insert(sel);
	}

}
