package model.dao.implments;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement("INSERT INTO SELLER" + " (Name, Email, BirthDate, BaseSalary, DepartmentId)"
					+ " VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, seller.getName());
			ps.setString(2, seller.getEmail());
			ps.setDate(3, Date.valueOf(seller.getBirthDate()));
			ps.setDouble(4, seller.getBaseSalary());
			ps.setInt(5, seller.getDepartment().getId());

			int rowAffected = ps.executeUpdate();

			if (rowAffected > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					seller.setId(id);
				}
				rs.close();
			} else {
				throw new DbException("Unexpected error! No row affected!");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBServices.closeStatement(ps);
		}

	}

	@Override
	public void update(Seller seller) {

		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("UPDATE SELLER SET"
					+ " Name = ?,"
					+ " Email = ?,"
					+ " BirthDate = ?,"
					+ " BaseSalary = ?,"
					+ " DepartmentId = ?"
					+ " WHERE Id = ?");
			
			ps.setString(1, seller.getName());
			ps.setString(2, seller.getEmail());
			ps.setDate(3, Date.valueOf(seller.getBirthDate()));
			ps.setDouble(4, seller.getBaseSalary());
			ps.setInt(5, seller.getDepartment().getId());
			ps.setInt(6, seller.getId());
			
			int rowsAffected = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBServices.closeStatement(ps);
		}
		
	}

	@Override
	public void deleteById(int id) {

		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement("DELETE FROM SELLER WHERE ID = ?");
			ps.setInt(1, id);

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected == 0) {
				throw new DbException("Unexpected error! Not deleted");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBServices.closeStatement(ps);
		}

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
	public List<Seller> findAll() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Seller> sellers = new ArrayList<>();
		Map<Integer, Department> departments = new HashMap<>();

		try {
			ps = conn.prepareStatement("SELECT S.*, D.NAME AS DEPARTMENT_NAME FROM SELLER AS S"
					+ " INNER JOIN DEPARTMENT AS D ON(S.DEPARTMENTID = D.ID)" + " ORDER BY NAME");

			rs = ps.executeQuery();

			while (rs.next()) {

				Department dep = departments.get(rs.getInt("DepartmentId"));

				if (dep == null) {
					dep = instantiateDepartment(rs);
					departments.put(rs.getInt("DepartmentId"), dep);
				}

				Seller seller = instantiateSeller(rs, dep);

				sellers.add(seller);
			}
			return sellers;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBServices.closeResultSet(rs);
			DBServices.closeStatement(ps);
		}
		return null;
	}

	@Override
	public List<Seller> findByDepartment(Department department) {

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
