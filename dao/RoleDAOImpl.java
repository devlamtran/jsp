package com.trangialam.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.trangialam.model.Role;
import com.trangialam.utilities.IDBManager;
import com.trangialam.utilities.MySQLDBManager;

public class RoleDAOImpl implements IRoleDAO {

	private IDBManager dbManager;

	public RoleDAOImpl() {
		this.dbManager = new MySQLDBManager();
	}

	public boolean saveRole(Role role) {
		// TODO Auto-generated method stub
		int rowsInserted = 0;
		String sql = "INSERT INTO role (name,description) VALUES (?, ?)";

		try {
			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
			statement.setString(1, role.getName());

			statement.setString(2, role.getDescription());

			rowsInserted = statement.executeUpdate();
			dbManager.closeStatement(statement);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("insert loi");
			return false;

		}
		if (rowsInserted > 0) {
			return true;
		} else {
			return false;
		}
	}

	public List<Role> getAllRoles() {
		// TODO Auto-generated method stub
		List<Role> listRoles = new ArrayList<Role>();
		String sql = "SELECT * FROM role";

		try {
			Statement statement = dbManager.getConnection().createStatement();

			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String description = resultSet.getString("description");

				Role role = new Role();
				role.setId(id);
				role.setName(name);
				;
				role.setDescription(description);
				;
				listRoles.add(role);
			}

			dbManager.closeStatement(resultSet, statement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return listRoles;
	}

	public Role getRoleById(int roleId) {
		// TODO Auto-generated method stub
		Role role = new Role();
		String sql = "SELECT * FROM role where id=?";

		try {
			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
			statement.setInt(1, roleId);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String description = resultSet.getString("description");

				role = new Role();
				role.setId(id);
				role.setName(name);
				;
				role.setDescription(description);
				;

			}

			dbManager.closeStatement(resultSet, statement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return role;
	}

	public boolean deleteRoleById(int roleId) {
		// TODO Auto-generated method stub
		int rowsDeleted = 0;
		String sql = "delete from role where id=?";

		try {
			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
			statement.setInt(1, roleId);

			rowsDeleted = statement.executeUpdate();
			dbManager.closeStatement(statement);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("delete loi");
			return false;

		}

		if (rowsDeleted > 0) {
			return true;
		} else {
			return false;
		}

	}

	public List<Integer> getAllUserRoleIdByRoleId(int roleId) {
		// TODO Auto-generated method stub
		List<Integer> listUserIds = new ArrayList<Integer>();
		String sql = "SELECT * FROM user_role where roleId =?";

		try {
			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
			statement.setInt(1, roleId);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int idUser = resultSet.getInt("userId");

				listUserIds.add(idUser);
			}

			dbManager.closeStatement(resultSet, statement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return listUserIds;
	}

	public boolean deleteUserRoleByRoleId(int roleId) {
		// TODO Auto-generated method stub
		int rowsDeleted = 0;
		String sql = "delete from  user_role where roleId=?";

		try {
			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
			statement.setInt(1, roleId);

			rowsDeleted = statement.executeUpdate();
			dbManager.closeStatement(statement);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("delete loi");
			return false;

		}

		if (rowsDeleted > 0) {
			return true;
		} else {
			return false;
		}

	}

	public List<Integer> getAllUserRoleIdByUserId(int userId) {
		// TODO Auto-generated method stub
		List<Integer> listRoleIds = new ArrayList<Integer>();
		String sql = "SELECT * FROM user_role where userId =?";

		try {
			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
			statement.setInt(1, userId);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int idRole = resultSet.getInt("roleId");

				listRoleIds.add(idRole);
			}

			dbManager.closeStatement(resultSet, statement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return listRoleIds;
	}

	public boolean insertUserRole(int userId, List<Integer> listRolesIdUnAssign) {
		// TODO Auto-generated method stub
		int rowsInserted = 0;
		String sql = "INSERT INTO user_role (userId,roleId) VALUES (?, ?)";
		for (Integer roleId : listRolesIdUnAssign) {
			try {
				PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
				statement.setInt(1, userId);

				statement.setInt(2, roleId);

				rowsInserted = statement.executeUpdate();
				dbManager.closeStatement(statement);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("insert loi");
				return false;

			}
		}

		if (rowsInserted > 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean deleteUserRole(int userId, List<Integer> listRolesIdAssignDelete) {
		// TODO Auto-generated method stub
		int rowsDeleted = 0;
		String sql = "delete from  user_role where roleId =? and userId=?";

		for (Integer roleId : listRolesIdAssignDelete) {
			try {
				PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
				statement.setInt(1, roleId);

				statement.setInt(2, userId);

				rowsDeleted += statement.executeUpdate();
				dbManager.closeStatement(statement);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("delete loi");
				return false;

			}
		}
		if (rowsDeleted == listRolesIdAssignDelete.size()) {
			return true;
		} else {
			return false;
		}
	}

	public Role getRoleByName(String nameRole) {
		// TODO Auto-generated method stub
		Role role = new Role();
		String sql = "SELECT * FROM role where name like ?";

		try {
			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
			statement.setString(1, nameRole);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String description = resultSet.getString("description");

				role = new Role();
				role.setId(id);
				role.setName(name);
				;
				role.setDescription(description);
				;

			}

			dbManager.closeStatement(resultSet, statement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return role;

	}

	public List<Role> getAllRolesByUserId(int userId) {
		// TODO Auto-generated method stub
		List<Role> listRoles = new ArrayList<Role>();
		String sql = "SELECT * FROM role,user_role where role.id = user_role.roleId and user_role.userId =? ";

		try {
			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
			statement.setInt(1, userId);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String description = resultSet.getString("description");

				Role role = new Role();
				role.setId(id);
				role.setName(name);
				;
				role.setDescription(description);
				;
				listRoles.add(role);
			}

			dbManager.closeStatement(resultSet, statement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return listRoles;
	}

	public boolean deleteUserRoleByUserId(int userId) {
		// TODO Auto-generated method stub
		int rowsDeleted = 0;
		String sql = "delete from  user_role where userId=?";

		try {
			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);

			statement.setInt(1, userId);

			rowsDeleted = statement.executeUpdate();
			dbManager.closeStatement(statement);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("delete loi");
			return false;

		}

		if (rowsDeleted > 0) {
			return true;
		} else {
			return false;
		}
	}

}
