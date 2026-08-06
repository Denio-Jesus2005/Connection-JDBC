package model.dao.implments;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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

		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement("INSERT INTO DEPARTMENT" + " (Name)" + " VALUES (?)",
					Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, dep.getName());

			int rowAffected = ps.executeUpdate();

			if (rowAffected > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					dep.setId(id);
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
	public void update(Department dep) {

		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement("UPDATE DEPARTMENT SET NAME = ? WHERE ID = ?");
			ps.setString(1, dep.getName());
			ps.setInt(2, dep.getId());

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected == 0) {
				throw new DbException("Update didn't realized!");
			}

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
			ps = conn.prepareStatement("DELETE FROM DEPARTMENT WHERE ID = ?");
			ps.setInt(1, id);
			
			int rowsAffected = ps.executeUpdate();
			
			if(rowsAffected < 0) {
				throw new DbException("Delete didn't realized");
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBServices.closeStatement(ps);
		}
		
		
		
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
					Department dp = instantiateDepartment(rs);
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

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		int depId = rs.getInt("Id");
		String name = rs.getString("Name");
		return new Department(depId, name);
	}

	@Override
	public List<Department> findAll() {

		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Department> departments = new ArrayList<>();

		try {
			ps = conn.prepareStatement("SELECT * FROM Department");

			rs = ps.executeQuery();

			while (rs.next()) {
				Department dep = instantiateDepartment(rs);
				departments.add(dep);
			}

			return departments;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBServices.closeStatement(ps);
			DBServices.closeResultSet(rs);
		}

		return null;
	}

}
