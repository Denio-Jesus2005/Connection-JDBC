package model.dao.implments;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.dao.DepartmentDAO;
import model.entities.Department;
import model.exceptions.DbException;
import services.DBServices;

public class DepartmentDAOJDBC implements DepartmentDAO {

	private Connection conn;

	public DepartmentDAOJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department dep) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Department dep) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Department findById(int id) {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("SELECT * FROM DEPARTMENT WHERE ID = ?");
			ps.setInt(1, id);

			rs = ps.executeQuery();
			if (conn != null) {
				if (rs.next()) {
					int depId = rs.getInt("Id");
					String name = rs.getString("Name");

					Department dp = new Department(depId, name);
					return dp;

				}

				if (rs.next()) {
					throw new DbException("Duplicate id department");
				} else {
					throw new DbException("Sem conexão com o banco");
				}
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
	public List<Department> findAll(Department dep) {
		// TODO Auto-generated method stub
		return null;
	}

}
