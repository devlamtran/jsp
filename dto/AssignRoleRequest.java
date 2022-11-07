package com.trangialam.dto;

import java.util.List;

public class AssignRoleRequest {
	private List<Integer> listRolesIdAssign;
	private int userId;
	public List<Integer> getListRolesIdAssign() {
		return listRolesIdAssign;
	}
	public void setListRolesIdAssign(List<Integer> listRolesIdAssign) {
		this.listRolesIdAssign = listRolesIdAssign;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
}
