package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Seller;

public interface SellerDAO {

	void insert(Seller seller);

	void update(Seller seller);

	void deleteById(int id);

	Seller findById(int id);

	List<Seller> findAll(Seller seller);

	List<Seller> finByDepartment(Department department);
}
