package com.trangialam.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.trangialam.model.Item;
import com.trangialam.model.Order;
import com.trangialam.utilities.IDBManager;
import com.trangialam.utilities.MySQLDBManager;

public class OrderDAOImpl implements IOrderDAO {
	
	private IDBManager dbManager;
	private IProductDAO productDAO;
	private int totalRecords;

	

	public OrderDAOImpl() {
		
		this.dbManager = new MySQLDBManager();
		this.productDAO = new ProductDAOImpl();
	}



	public boolean saveOrder(Order order) {
		// TODO Auto-generated method stub
		int rowsInserted = 0;
		String sql = "INSERT INTO shop.order(userId,sessionId,token,subTotal,firstName,middleName,lastName,mobile,email,city,province,country,createdAt,content) VALUES (?, ?, ?, ? , ? , ? , ? , ? , ?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
			statement.setInt(1, order.getCustomer().getId());
			statement.setString(2, order.getSessionId());
			statement.setString(3, order.getToken());
			statement.setBigDecimal(4, order.getPrice());
			statement.setString(5, order.getFirstName());
			statement.setString(6, order.getMiddleName());
			statement.setString(7, order.getLastName());
			statement.setString(8, order.getMobile());
			statement.setString(9, order.getEmail());
			statement.setString(10, order.getCity());
			statement.setString(11, order.getProvince());
			statement.setString(12, order.getCountry());
			statement.setString(13, order.getCreatedAt());
			statement.setString(14, order.getContent());
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



	public int getTotalRecords() {
		return totalRecords;
	}



	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}



	public List<Order> getOrderByUserIdLimit(int offset, int records, int userId) {
		// TODO Auto-generated method stub
		List<Order> listOrder = new ArrayList<Order>();
		String sql = "select * from shop.order where userId = "+ userId + " limit " + offset + "," + records;

		try {

			Statement statement = dbManager.getConnection().createStatement();

			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String lastName = resultSet.getString("lastName");
				String firstName = resultSet.getString("firstName");
				String email = resultSet.getString("email");
				String mobile = resultSet.getString("mobile");
				BigDecimal price = resultSet.getBigDecimal("subTotal");
				String city = resultSet.getString("city");
				String createdAt = resultSet.getString("createdAt");
				int status = resultSet.getInt("status");

				Order order = new Order();
				order.setId(id);
				order.setLastName(lastName);
				order.setFirstName(firstName);;
				order.setEmail(email);
				order.setMobile(mobile);
				order.setCity(city);
				order.setStatus(status);
				order.setCreatedAt(createdAt);
				order.setPrice(price);
				// System.out.println(user.toString());
				listOrder.add(order);
			}
			dbManager.closeResultSet(resultSet);
			resultSet = statement.executeQuery("SELECT COUNT(*) FROM shop.order where userId ="  + userId);
			if (resultSet.next()) {
				this.setTotalRecords(resultSet.getInt(1));
			}
			dbManager.closeStatement(resultSet, statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listOrder;
	}



	public List<Item> getItemsByOrderId(int orderId) {
		// TODO Auto-generated method stub
		List<Item> listItems = new ArrayList<Item>();
		String sql = "select * from shop.order_item where orderId =? ";

		try {

			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
            statement.setInt(1, orderId);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int productId = resultSet.getInt("productId");
				int quantity = resultSet.getInt("quantity");
				BigDecimal price = resultSet.getBigDecimal("price");

				Item item = new Item();
				item.setProduct(productDAO.getProductById(productId));
				item.setQuantity(quantity);
				item.setPrice(price);
				// System.out.println(user.toString());
				listItems.add(item);
			}
			
			dbManager.closeStatement(resultSet, statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listItems;
	}



	public boolean saveOrderItems(List<Item> items,String sessionId) {
		// TODO Auto-generated method stub
		int rowsInserted = 0;
		String sql = "INSERT INTO shop.order_item(productId,orderId,price,quantity,createdAt) VALUES (?, ?, ?, ? ,?)";

		for (Item item : items) {
			try {
				PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
				statement.setInt(1, item.getProduct().getId());
				statement.setInt(2, getOrderId(sessionId) );
				statement.setBigDecimal(3, item.getPrice());
				statement.setInt(4, item.getQuantity());
				statement.setString(5, item.getProduct().getCreatedAt());
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



	public int getOrderId(String sessionId) {
		// TODO Auto-generated method stub
		int orderId = 0;
		String sql = "select * from shop.order where sessionId =? ";

		try {

			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
            statement.setString(1, sessionId);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				orderId = resultSet.getInt("id");
				
			}
			
			dbManager.closeStatement(resultSet, statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return orderId;
	}



	public List<Order> getAllOrderLimit(int offset, int records) {
		// TODO Auto-generated method stub
		List<Order> listOrder = new ArrayList<Order>();
		String sql = "select * from shop.order limit " + offset + "," + records;

		try {

			Statement statement = dbManager.getConnection().createStatement();

			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String lastName = resultSet.getString("lastName");
				String firstName = resultSet.getString("firstName");
				String email = resultSet.getString("email");
				String mobile = resultSet.getString("mobile");
				BigDecimal price = resultSet.getBigDecimal("subTotal");
				String city = resultSet.getString("city");
				String createdAt = resultSet.getString("createdAt");
				int status = resultSet.getInt("status");

				Order order = new Order();
				order.setId(id);
				order.setLastName(lastName);
				order.setFirstName(firstName);;
				order.setEmail(email);
				order.setMobile(mobile);
				order.setCity(city);
				order.setStatus(status);
				order.setCreatedAt(createdAt);
				order.setPrice(price);
			
				listOrder.add(order);
			}
			dbManager.closeResultSet(resultSet);
			resultSet = statement.executeQuery("SELECT COUNT(*) FROM shop.order");
			if (resultSet.next()) {
				this.setTotalRecords(resultSet.getInt(1));
			}
			dbManager.closeStatement(resultSet, statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listOrder;
	}



	public boolean deleteOrderById(int orderId) {
		// TODO Auto-generated method stub
		int rowsDeleted = 0;
		String sql = "delete from shop.order where id =? ";

		try {
			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
			statement.setInt(1, orderId);

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



	public List<Integer> getAllOrderItemIdByOrderId(int orderId) {
		// TODO Auto-generated method stub
		List<Integer> listOrderItemIds = new ArrayList<Integer>();
		String sql = "SELECT * FROM shop.order_item where orderId =?";

		try {
			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
			statement.setInt(1, orderId);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");

				listOrderItemIds.add(id);
			}

			dbManager.closeStatement(resultSet, statement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return listOrderItemIds;
	}



	public boolean deleteOrderItemById(List<Integer> listOrderItemIdOrder) {
		// TODO Auto-generated method stub
		int rowsDeleted = 0;
		String sql = "delete from  shop.order_item where id =?";

		for (Integer id : listOrderItemIdOrder) {
			try {
				PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
				statement.setInt(1, id);

				rowsDeleted += statement.executeUpdate();
				dbManager.closeStatement(statement);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("delete loi");
				return false;

			}
		}
		if (rowsDeleted == listOrderItemIdOrder.size()) {
			return true;
		} else {
			return false;
		}
	}



	public Order getOrderById(int orderId) {
		// TODO Auto-generated method stub
		Order order = new Order();
		String sql = "select * from shop.order where id=? ";

		try {

			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
            statement.setInt(1, orderId);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String lastName = resultSet.getString("lastName");
				String firstName = resultSet.getString("firstName");
				String email = resultSet.getString("email");
				String mobile = resultSet.getString("mobile");
				BigDecimal price = resultSet.getBigDecimal("subTotal");
				String city = resultSet.getString("city");
				String createdAt = resultSet.getString("createdAt");
				int status = resultSet.getInt("status");

				
				order.setId(id);
				order.setLastName(lastName);
				order.setFirstName(firstName);;
				order.setEmail(email);
				order.setMobile(mobile);
				order.setCity(city);
				order.setStatus(status);
				order.setCreatedAt(createdAt);
				order.setPrice(price);
			
				
			}
			
			dbManager.closeStatement(resultSet, statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return order;
	}



	

}
