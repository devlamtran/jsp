package com.trangialam.dto;

import java.util.List;

import com.trangialam.model.Category;



public class GetAllCategoryRequest {
	private List<Category> listCategories;

	public List<Category> getlistCategories() {
		return listCategories;
	}

	public void setListCategories(List<Category> listCategories) {
		this.listCategories = listCategories;
	}

	
	

}
