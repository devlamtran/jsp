package com.trangialam.dto;

import java.util.List;

import com.trangialam.model.Order;

public class GetAllOrderCustomerRequest {
	
	private List<Order> listOrders;

	public List<Order> getListOrders() {
		return listOrders;
	}

	public void setListOrders(List<Order> listOrders) {
		this.listOrders = listOrders;
	}
	
	

}
