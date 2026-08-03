package application;

import java.util.List;

import model.dao.DAOFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		SellerDAO sellerDao = DAOFactory.createSellerDAO();

		Department department = new Department(1, "João");
		
		List<Seller> sellers = sellerDao.finByDepartment(department);
		for(Seller seller : sellers) {
			System.out.println(seller);
		}
	}

}
