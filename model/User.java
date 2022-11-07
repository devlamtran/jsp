package com.trangialam.model;

import java.util.List;

public class User {

	private int id;
	private String firstName;
	private String middleName;
	private String lastName;
	private String userName;
	private String email;
	private String mobile;
	private String password;
	private String linkImage;
	private String registerDay;
	private List<Role> listRole;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRegisterDay() {
		return registerDay;
	}
	public void setRegisterDay(String registerDay) {
		this.registerDay = registerDay;
	}
	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName + ", userName="
				+ userName + ", email=" + email + ", mobile=" + mobile + ", password=" + password + ", registerDay="
				+ registerDay + "]";
	}
	public List<Role> getListRole() {
		return listRole;
	}
	public void setListRole(List<Role> listRole) {
		this.listRole = listRole;
	}
	
	public boolean isAdmin() {
		boolean isAdmin = false;
		for (Role role : listRole) {
			if (role.getName().equalsIgnoreCase("Admin")) {
				isAdmin = true;
			}
		}
		return isAdmin;
	}
	public String getLinkImage() {
		return linkImage;
	}
	public void setLinkImage(String linkImage) {
		this.linkImage = linkImage;
	}
	
	
	
	
	
	
	
}
