package com.trangialam.dto;

import java.util.List;

public class AssignCategoryRequest {
	
	private List<Integer> listCategoriesIdAssign;
	private int categoryId;
	
	public List<Integer> getListCategoriesIdAssign() {
		return listCategoriesIdAssign;
	}
	public void setListCategoriesAssign(List<Integer> listCategoriesIdAssign) {
		this.listCategoriesIdAssign = listCategoriesIdAssign;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	

}
