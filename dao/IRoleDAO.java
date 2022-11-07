package com.trangialam.dao;

import java.util.List;

import com.trangialam.model.Role;

public interface IRoleDAO {

	public boolean saveRole(Role role);

	public List<Role> getAllRoles();

	public Role getRoleById(int roleId);

	public boolean deleteRoleById(int roleId);

//	public boolean updateRoleIdByUserIds(List<Integer> listUsersIdAssigned);

	public List<Integer> getAllUserRoleIdByRoleId(int roleId);

	public boolean deleteUserRoleByRoleId(int roleId);

	public List<Integer> getAllUserRoleIdByUserId(int userId);

	public boolean insertUserRole(int userId, List<Integer> listRolesIdUnAssign);

	public boolean deleteUserRole(int userId, List<Integer> listRolesIdAssignDelete);

	public Role getRoleByName(String roleName);

	public List<Role> getAllRolesByUserId(int userId);

	public boolean deleteUserRoleByUserId(int userId);

}
