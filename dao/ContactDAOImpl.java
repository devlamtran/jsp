package com.trangialam.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.trangialam.model.Category;
import com.trangialam.model.Contact;
import com.trangialam.model.Product;
import com.trangialam.utilities.IDBManager;
import com.trangialam.utilities.MySQLDBManager;

public class ContactDAOImpl implements IContactDAO {
	private IDBManager dbManager;
	private int totalRecords;
	
	
	public ContactDAOImpl() {
	
		this.dbManager = new MySQLDBManager();
	}


	public boolean saveContact(Contact contact) {
		// TODO Auto-generated method stub
		int rowsInserted = 0;
		String sql = "INSERT INTO contact (name,email,subject,message) VALUES (?, ?, ?, ?)";

		try {
			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
			statement.setString(1, contact.getName());

			statement.setString(2, contact.getEmail());
			
			statement.setString(3, contact.getSubject());
			
			statement.setString(4, contact.getMessage());

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


	public List<Contact> getContactsLimit(int offset, int records) {
		// TODO Auto-generated method stub
		List<Contact> listContacts = new ArrayList<Contact>();
		String sql = "select * from contact limit " + offset + ", " + records;

		try {

			Statement statement = dbManager.getConnection().createStatement();

			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				int id = resultSet.getInt("id");

				String name = resultSet.getString("name");

				String email = resultSet.getString("email");
				
				String subject = resultSet.getString("subject");
				
				String message = resultSet.getString("message");

				Contact contact = new Contact();
				contact.setId(id);
				contact.setName(name);
				contact.setSubject(subject);
				contact.setEmail(email);
				contact.setMessage(message);

				
				// System.out.println(user.toString());
				listContacts.add(contact);
			}
			dbManager.closeResultSet(resultSet);
			resultSet = statement.executeQuery("SELECT COUNT(*) FROM contact");
			if (resultSet.next()) {
				this.setTotalRecords(resultSet.getInt(1));
			}
			dbManager.closeStatement(resultSet, statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listContacts;
	}


	private void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
		
	}
	
	public int getTotalRecords() {
		return totalRecords;
	}


	public Contact getContactById(int contactId) {
		// TODO Auto-generated method stub
		Contact contact = null;
		String sql = "select * from contact where id = " + contactId;

		try {

			Statement statement = dbManager.getConnection().createStatement();

			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String email = resultSet.getString("email");
				String message = resultSet.getString("message");
				String subject = resultSet.getString("subject");
				

				contact = new Contact();
				contact.setId(id);
				contact.setName(name);;
				contact.setEmail(email);;
				contact.setSubject(subject);;
				contact.setMessage(message);;
				
				// System.out.println(user.toString());

			}

			dbManager.closeStatement(resultSet, statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return contact;
	}

}
