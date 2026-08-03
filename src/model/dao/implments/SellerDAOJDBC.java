package model.dao.implments;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;
import model.exceptions.DbException;
import services.DBServices;

public class SellerDAOJDBC implements SellerDAO {

	private Connection conn = null;

	public SellerDAOJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller seller) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Seller seller) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Seller findById(int id) {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			if (conn != null) {
				ps = conn.prepareStatement("SELECT SELLER.*, DEPARTMENT.NAME AS DEPARTMENT_NAME FROM SELLER"
						+ " INNER JOIN DEPARTMENT ON(SELLER.DEPARTMENTID = DEPARTMENT.ID) WHERE SELLER.ID = ?");
				ps.setInt(1, id);

				rs = ps.executeQuery();
				if (rs.next()) {
					Department dep = instantiateDepartment(rs);
					Seller sl = instantiateSeller(rs, dep);
					return sl;
				}
				if (rs.next()) {
					throw new DbException("Duplicate id seller");
				}
			} else {
				throw new DbException("Sem conexão com o banco");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DBServices.closeResultSet(rs);
			DBServices.closeStatement(ps);
		}
		return null;
	}

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		int slId = rs.getInt("Id");
		String name = rs.getString("Name");
		String email = rs.getString("email");
		LocalDate birthDate = rs.getDate("birthDate").toLocalDate();
		double baseSalary = rs.getDouble("BaseSalary");
		return new Seller(slId, name, email, birthDate, baseSalary, dep);
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		int id = rs.getInt("DepartmentId");
		String name = rs.getString("Department_Name");
		return new Department(id, name);

	}

	@Override
	public List<Seller> findAll(Seller seller) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Seller> finByDepartment(Department department) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Seller> sellers = new ArrayList<>();
		Map<Integer, Department> departments = new HashMap<>();

		try {
			ps = conn.prepareStatement("SELECT S.*, D.NAME AS DEPARTMENT_NAME FROM SELLER AS S "
					+ "INNER JOIN DEPARTMENT AS D ON (S.DEPARTMENTID = D.ID) " + "WHERE DEPARTMENTID = ? "
					+ "ORDER BY NAME");
			ps.setInt(1, department.getId());

			rs = ps.executeQuery();
			while (rs.next()) {
				Department dep = departments.get(department.getId());
				if (dep == null) {
					dep = instantiateDepartment(rs);
				}
				Seller sl = instantiateSeller(rs, dep);
				sellers.add(sl);

			}
			return sellers;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
