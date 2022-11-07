package com.trangialam.dto;

import java.util.List;

import com.trangialam.model.Contact;

public class GetAllContactRequest {
	
	private List<Contact> listContacts;

	public List<Contact> getListContacts() {
		return listContacts;
	}

	public void setListContacts(List<Contact> listContacts) {
		this.listContacts = listContacts;
	}
	
	

}
