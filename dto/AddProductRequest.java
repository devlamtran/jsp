package com.trangialam.dto;

import java.math.BigDecimal;

public class AddProductRequest {
	
	private String title;
	private String linkImage;
	private BigDecimal price;
	private BigDecimal discount;
	private int quantity;
	private String content;
	private int userId;
	private String brand;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getLinkImage() {
		return linkImage;
	}
	public void setLinkImage(String linkImage) {
		this.linkImage = linkImage;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getDiscount() {
		return discount;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "AddProductRequest [title=" + title + ", linkImage=" + linkImage + ", price=" + price + ", discount="
				+ discount + ", quantity=" + quantity + ", content=" + content + ", userId=" + userId + ", brand="
				+ brand + "]";
	}
	
	
	
	

}
