package com.trangialam.dto;

import java.util.List;


public class AssignProductRequest {
	
	private List<Integer> listCategoriesIdAssign;
	private int productId;
	
	public List<Integer> getListCategoriesIdAssign() {
		return listCategoriesIdAssign;
	}
	public void setListCategoriesAssign(List<Integer> listCategoriesIdAssign) {
		this.listCategoriesIdAssign = listCategoriesIdAssign;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	
	
		

}
