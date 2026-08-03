package model.dao.implments;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

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
					int slId = rs.getInt("Id");
					String name = rs.getString(2);
					String email = rs.getString(3);
					LocalDate birthDate = rs.getDate(4).toLocalDate();
					double baseSalary = rs.getDouble(5);
					int departmentId = rs.getInt(6);
					String depName = rs.getString(7);

					Department dep = new Department(departmentId, depName);
					Seller sl = new Seller(slId, name, email, birthDate, baseSalary, dep);

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

	@Override
	public List<Seller> findAll(Seller seller) {
		// TODO Auto-generated method stub
		return null;
	}

}
