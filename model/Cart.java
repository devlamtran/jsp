package com.trangialam.model;

import java.util.HashMap;
import java.util.Map;

public class Cart {

	private Map<String, Integer> listItem;

	public Cart() {

		listItem = new HashMap<String, Integer>();
	}

	public void addItem(Product item) {
		String productId = String.valueOf(item.getId());

		if (listItem.containsKey(productId)) {
			listItem.put(productId, listItem.get(productId) + 1);
		} else {
			listItem.put(productId, 1);
		}
	}
	
	public void deleteItem(Product item) {
		String productId = String.valueOf(item.getId());

		if (listItem.containsKey(productId)) {
			listItem.put(productId, listItem.get(productId) + 1);
		} else {
			listItem.put(productId, 1);
		}
	}

	public Map<String, Integer> getListItem() {
		return listItem;
	}

	public void setListItem(Map<String, Integer> listItem) {
		this.listItem = listItem;
	}

}
