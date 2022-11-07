package com.trangialam.dto;

import java.util.List;


public class GetAllProductCategoryIdRequest {
	
	private List<Integer> listCategoryIDs;

	public List<Integer> getListCategoryIDs() {
		return listCategoryIDs;
	}

	public void setListCategoryIDs(List<Integer> listCategoryIDs) {
		this.listCategoryIDs = listCategoryIDs;
	}

}
