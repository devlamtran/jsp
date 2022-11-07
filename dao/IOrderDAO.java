package com.trangialam.dao;

import java.util.List;

import com.trangialam.model.Item;
import com.trangialam.model.Order;

public interface IOrderDAO {

	public boolean saveOrder(Order order);

	public List<Order> getOrderByUserIdLimit(int offset, int records, int userId);

	public int getTotalRecords();

	public List<Item> getItemsByOrderId(int orderId);

	public boolean saveOrderItems(List<Item> items, String sessionId);
	
	public int getOrderId(String sessionId);

	public List<Order> getAllOrderLimit(int offset, int records);

	public boolean deleteOrderById(int orderId);

	public List<Integer> getAllOrderItemIdByOrderId(int orderId);

	public boolean deleteOrderItemById(List<Integer> listOrderItemIdOrder);

	public Order getOrderById(int orderId);

	

}
