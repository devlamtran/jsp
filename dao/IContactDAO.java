package com.trangialam.dao;

import java.util.List;

import com.trangialam.model.Contact;

public interface IContactDAO {

	public boolean saveContact(Contact contact);

	public List<Contact> getContactsLimit(int offset, int records);

	public int getTotalRecords();

	public Contact getContactById(int contactId);

}
