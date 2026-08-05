package application;

import java.util.List;

import model.dao.DAOFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

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

		System.out.println("----------------------TEST 2: FIND ALL----------------------");

		sellers = sellerDao.findAll();

		for (Seller seller : sellers) {
			System.out.println(seller);
		}
	}

}
