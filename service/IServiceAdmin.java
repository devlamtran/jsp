package com.trangialam.service;

import java.util.List;

import com.trangialam.dto.AddCategoryRequest;
import com.trangialam.dto.AddProductRequest;
import com.trangialam.dto.AddRoleRequest;
import com.trangialam.dto.AssignCategoryRequest;
import com.trangialam.dto.AssignProductRequest;
import com.trangialam.dto.AssignRoleRequest;
import com.trangialam.dto.DeleteCategoryRequest;
import com.trangialam.dto.DeleteOrderRequest;
import com.trangialam.dto.DeleteProductRequest;
import com.trangialam.dto.DeleteRoleRequest;
import com.trangialam.dto.DeleteUserRequest;
import com.trangialam.dto.EditCategoryRequest;
import com.trangialam.dto.EditProductRequest;
import com.trangialam.dto.PagingRequest;
import com.trangialam.model.Category;
import com.trangialam.model.Contact;
import com.trangialam.model.Item;
import com.trangialam.model.Order;
import com.trangialam.model.Product;
import com.trangialam.model.Role;
import com.trangialam.model.User;

public interface IServiceAdmin {
	
	public boolean addProduct(AddProductRequest addProductRequest);

	public List<Product> getAllProducts();

	public List<Product> getProductsPaging(PagingRequest pagingRequest);
	
	public int getTotalPagesProduct(PagingRequest pagingRequest);
	public int getTotalPagesUser(PagingRequest pagingRequest);
	
	public boolean editProduct(EditProductRequest editProductRequest);

	public Product getProductById(int productId);

	public boolean deleteProduct(DeleteProductRequest deleteProductRequest);
	
	public List<User> getUsersPaging(PagingRequest pagingRequest);

	public boolean addCategory(AddCategoryRequest addCategoryRequest);

	public List<Category> getCategoriesPaging(PagingRequest pagingRequest);

	public int getTotalPagesCategory(PagingRequest pagingRequest);

	public List<Category> getAllCategory();

	public boolean assignProduct(AssignProductRequest assignProductRequest);

	public List<Integer> getAllProductCategoryIdByProductId(int productId);

	public Category getCategoryById(int categoryId);

	public boolean deleteCategory(DeleteCategoryRequest deleteCategoryRequest);

	public boolean editCategory(EditCategoryRequest editCategoryRequest);

	public List<Integer> getAllCategoryParentIdByCategoryId(int categoryId);

	public boolean assignCategory(AssignCategoryRequest assignCategoryRequest);

	public boolean addRole(AddRoleRequest addRoleRequest);

	public List<Role> getAllRole();

	public Role getRoleById(int roleId);

	public boolean deleteRole(DeleteRoleRequest deleteRoleRequest);

	public List<Integer> getAllUserRoleIdByUserId(int userId);

	public User getUserById(int userId);


	public boolean assignRole(AssignRoleRequest assignRoleRequest);

	public boolean deleteUser(DeleteUserRequest deleteUserRequest);

	public List<Order> getAllOrdersPaging(PagingRequest pagingRequest);

	public int getTotalPagesOrder(PagingRequest pagingRequest);

	public List<Item> getItemsInOrder(int orderId);

	public boolean deleleOrder(DeleteOrderRequest deleteOrderRequest);

	public List<Product> getProductCategoryPaging(PagingRequest pagingRequest, int categoryId);

	public int getTotalPagesProductCategory(PagingRequest pagingRequest);

	public boolean deleteUserRole(int userId);

	public boolean deleteProductCategory(int productId);

	public List<Contact> getContactsPaging(PagingRequest pagingRequest);

	public int getTotalPagesContact(PagingRequest pagingRequest);

	public Contact getContactById(int contactId);


	

}
