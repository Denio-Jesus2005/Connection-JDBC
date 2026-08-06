package application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import model.dao.DAOFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class SellerApp {

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
		sel = new Seller(null, "UPDATED", "denios", LocalDate.parse("07/11/2005", fmt), 3.200, department);
		
		sellerDao.insert(sel);
		System.out.println("Inserted");
		System.out.println();
		
		System.out.println("----------------------TEST 5: UPDATE----------------------");
		
		Seller sel1 = new Seller(6, "UPDATED", "UPDATED", LocalDate.parse("01/01/2001", fmt), 10, new Department(4, null));
		
		sellerDao.update(sel1);
		System.out.println("Updated");
		
		System.out.println("----------------------TEST 6: DELETE BY ID----------------------");
		
		sellerDao.deleteById(7);
		System.out.println("Deleted");
	}

}
