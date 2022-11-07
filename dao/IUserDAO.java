package com.trangialam.dao;

import java.sql.SQLException;
import java.util.List;

import com.trangialam.model.Role;
import com.trangialam.model.User;

public interface IUserDAO {
	
	public List<User> getAllUsers() throws SQLException;
	public User getUserByUserName(String userName);
	public User getUserByEmail(String email);
	public User getUserByPhone(String phone);
	public User getUserAfterLogin(String keyInformation);
	public boolean saveUser(User user);
	public void deleteUser(User user);
	public boolean checkPasswordUser(User user);
	public List<User> getUsersLimit(int offset, int records);
	public int getTotalRecords();
	public User getUserById(int userId);
	public boolean deleteUserById(int userId);
	public boolean deleteUserRoleByUserId(int userId);
	public boolean saveUserRole(User user, Role role);
	public List<Role> getListRolesByUserId(int userId);
	public boolean updateUser(User user);
	public boolean updateUserProfile(User user);
	

}
