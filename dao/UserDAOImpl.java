package com.trangialam.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.trangialam.model.Role;
import com.trangialam.model.User;
import com.trangialam.utilities.HashPassword;
import com.trangialam.utilities.IDBManager;
import com.trangialam.utilities.MySQLDBManager;

public class UserDAOImpl implements IUserDAO {

	private IDBManager dbManager;
	private int totalRecords;

	public UserDAOImpl() {

		this.dbManager = new MySQLDBManager();
	}

	public List<User> getAllUsers() throws SQLException {
		// TODO Auto-generated method stub
		List<User> listUser = new ArrayList<User>();
		String sql = "SELECT * FROM user";
		Statement statement = dbManager.getConnection().createStatement();
		ResultSet resultSet = statement.executeQuery(sql);

		while (resultSet.next()) {
			int id = resultSet.getInt("id");
			String lastName = resultSet.getString("lastName");
			String userName = resultSet.getString("username");
			String email = resultSet.getString("email");
			String mobile = resultSet.getString("mobile");

			User user = new User();
			// System.out.println(user.toString());
			listUser.add(user);
		}

		dbManager.closeStatement(resultSet, statement);

		return listUser;
	}

	public User getUserByUserName(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	public User getUserByPhone(String phone) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean saveUser(User user) {
		// TODO Auto-generated method stub
		int rowsInserted = 0;
		String sql = "INSERT INTO user (firstName,middleName,lastName,username,mobile,email, passwordHash, registeredAt) VALUES (?, ?, ?, ? , ? , ? , ? , ?)";

		try {
			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getMiddleName());
			statement.setString(3, user.getLastName());
			statement.setString(4, user.getUserName());
			statement.setString(5, user.getMobile());
			statement.setString(6, user.getEmail());
			statement.setString(7, HashPassword.generatedPasswordHash(user.getPassword()));
			statement.setString(8, user.getRegisterDay());
			rowsInserted = statement.executeUpdate();
			dbManager.closeStatement(statement);
			;

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

	public void deleteUser(User user) {
		// TODO Auto-generated method stub

	}

	public boolean checkPasswordUser(User user) {
		User userInDatabase = new User();
		String sql = "SELECT * FROM user WHERE username =? or mobile =? or email =?";

		try {
			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
			statement.setString(1, user.getUserName());
			statement.setString(2, user.getMobile());
			statement.setString(3, user.getEmail());

			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next()) {
				dbManager.closeStatement(statement);
				return false;
			} else {

				String password = resultSet.getString("passwordHash");

				userInDatabase.setPassword(password);

				dbManager.closeStatement(statement);
				return userInDatabase.getPassword().equals(HashPassword.generatedPasswordHash(user.getPassword()));

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("insert loi");

		}

		return userInDatabase.getPassword().equals(HashPassword.generatedPasswordHash(user.getPassword()));

	}

	public User getUserAfterLogin(String keyInformation) {
		// TODO Auto-generated method stub
		User userInDatabase = new User();
		String sql = "SELECT * FROM user WHERE username =? or mobile =? or email =?";

		try {
			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
			statement.setString(1, keyInformation);
			statement.setString(2, keyInformation);
			statement.setString(3, keyInformation);

			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String lastName = resultSet.getString("lastName");
				String userName = resultSet.getString("username");
				String email = resultSet.getString("email");
				String mobile = resultSet.getString("mobile");
				String firstName = resultSet.getString("firstName");
				String middleName = resultSet.getString("middleName");
				String linkImage = resultSet.getString("linkImage");
				
                userInDatabase.setId(id);
				userInDatabase.setLastName(lastName);
				userInDatabase.setEmail(email);
				userInDatabase.setMobile(mobile);
				userInDatabase.setUserName(userName);
				userInDatabase.setFirstName(firstName);
				userInDatabase.setMiddleName(middleName);
				userInDatabase.setLinkImage(linkImage);
			}

			dbManager.closeStatement(statement);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("insert loi");

		}

		return userInDatabase;

	}

	public List<User> getUsersLimit(int offset, int records) {
		
		List<User> listUser = new ArrayList<User>();
		String sql = "select * from user limit " + offset + ", " + records;

		try {

			Statement statement = dbManager.getConnection().createStatement();

			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String firstName = resultSet.getString("firstName");
				String lastName = resultSet.getString("lastName");
				String userName = resultSet.getString("username");
				String mobile = resultSet.getString("mobile");
				String email = resultSet.getString("email");
				String registeredDay = resultSet.getString("registeredAt");
			

				User user = new User();
				user.setId(id);
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setUserName(userName);
				user.setMobile(mobile);;
				user.setEmail(email);
				//user.setAdmin(admin);
				user.setRegisterDay(registeredDay);
				
				// System.out.println(user.toString());
				listUser.add(user);
			}
			dbManager.closeResultSet(resultSet);
			resultSet = statement.executeQuery("SELECT COUNT(*) FROM user");
			if (resultSet.next()) {
				this.setTotalRecords(resultSet.getInt(1));
			}
			dbManager.closeStatement(resultSet, statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listUser;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public User getUserById(int userId) {
		// TODO Auto-generated method stub
		User user = null;
		String sql = "select * from user where id=? ";

		try {

			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
			statement.setInt(1, userId);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String firstName = resultSet.getString("firstName");
				String lastName = resultSet.getString("lastName");
				String userName = resultSet.getString("username");
				String mobile = resultSet.getString("mobile");
				String email = resultSet.getString("email");
				String registeredDay = resultSet.getString("registeredAt");
			

			    user = new User();
				user.setId(id);
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setUserName(userName);
				user.setMobile(mobile);;
				user.setEmail(email);
				user.setRegisterDay(registeredDay);
				
				// System.out.println(user.toString());
				
			}
			dbManager.closeResultSet(resultSet);
			resultSet = statement.executeQuery("SELECT COUNT(*) FROM user");
			if (resultSet.next()) {
				this.setTotalRecords(resultSet.getInt(1));
			}
			dbManager.closeStatement(resultSet, statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

	public boolean deleteUserById(int userId) {
		// TODO Auto-generated method stub
		int rowsDeleted = 0;
		String sql = "delete from user where id=?";

		
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
		
		if (rowsDeleted >0) {
			return true;
		} else {
			return false;
		}
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
		
		if (rowsDeleted >0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean saveUserRole(User user,Role role) {
		int rowsInserted = 0;
		String sql = "INSERT INTO user_role (userId, roleId) VALUES (?, ?)";

		try {
			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
			statement.setInt(1, user.getId());
			statement.setInt(2, role.getId());
			
			rowsInserted = statement.executeUpdate();
			dbManager.closeStatement(statement);
			;

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

	public List<Role> getListRolesByUserId(int userId) {
		// TODO Auto-generated method stub
		List<Role> listRole = new ArrayList<Role>();
		String sql = "select * from role,user_role where role.id = user_role.roleId and user_role.userId =? ";

		try {

			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
            statement.setInt(1,userId);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				
			

				Role role = new Role();
				role.setId(id);
				role.setName(name);
				
				
				listRole.add(role);
			}
			
			dbManager.closeStatement(resultSet, statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listRole;
	}

	public boolean updateUser(User user) {
		// TODO Auto-generated method stub
		int rowsUpdated = 0;
		String sql = "UPDATE user SET passwordHash = ? WHERE username = ?";

		try {
			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);

			
			statement.setString(1,HashPassword.generatedPasswordHash(user.getPassword()));
			statement.setString(2, user.getUserName());
			rowsUpdated = statement.executeUpdate();
			dbManager.closeStatement(statement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		if (rowsUpdated >= 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean updateUserProfile(User user) {
		// TODO Auto-generated method stub
		int rowsUpdated = 0;
		String sql = "UPDATE user SET firstName= ?,middleName= ?,lastName= ?,mobile= ?,email = ?,linkImage= ?,username= ? WHERE id= ?";

		try {
			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);

			
			statement.setString(1,user.getFirstName());
			statement.setString(2, user.getMiddleName());
			statement.setString(3, user.getLastName());
			statement.setString(4, user.getMobile());
			statement.setString(5, user.getEmail());
			statement.setString(6, user.getLinkImage());
			statement.setString(7, user.getUserName());
			statement.setInt(8, user.getId());
			rowsUpdated = statement.executeUpdate();
			dbManager.closeStatement(statement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		if (rowsUpdated >= 0) {
			return true;
		} else {
			return false;
		}
	}

	

	

}
