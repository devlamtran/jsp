package com.trangialam.dao;

import java.util.List;

import com.trangialam.dto.EditCategoryRequest;
import com.trangialam.model.Category;

public interface ICategoryDAO {
	
	public List<Category> getAllCategories();
	public boolean saveCategory(Category category);
	public List<Category> getCategoriesLimit(int offset, int records);
	public int getTotalRecords();
	public boolean insertProductCategory(int productId, List<Integer> listCategoriesId);
	public List<Integer> getAllProductCategoryIdByProductId(int productId);
	public boolean updateProductCategory(int productId, List<Integer> listCategoriesIdAssignUpdate);
	public boolean deleteProductCategory(int productId, List<Integer> listCategoriesIdAssignDelete);
	public boolean deleteProductCategoryByProductId(int productId);
	public Category getCategoryById(int categoryId);
	public boolean deleteCategoryById(int categoryId);
	public List<Integer> getAllProductCategoryIdByCategoryId(int categoryId);
	public boolean deleteProductCategoryByCategoryId(int categoryId);
	public boolean updateCategory(Category category);
	public List<Integer> getAllCategoryParentIdByCategoryId(int categoryId);
	public boolean updateParentIdCategory(Category category);
	public List<Integer> getAllCategoryIdHasParentId(int categoryId);
	public boolean updateParentIdByCategoryId(int categoryId);
	public boolean updateParentIdByCategoryId(List<Integer> listCategoriesIdAssigned);
	
	

}
