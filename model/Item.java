package com.trangialam.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Item {
	
	private int id;
	private Product product;
	private int quantity;
	private BigDecimal price;
	private DecimalFormat formatter = new DecimalFormat("###,###,###");

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public String toStringPrice() {

		return formatter.format(this.price) + " ð";
	};
	
	public String toStringTotalPrice() {

		return formatter.format(this.price.doubleValue() * this.quantity) + " ð";
	}
	
	
	
	
	
	
	
	

}
