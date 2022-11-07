package com.trangialam.service;



import java.math.BigDecimal;
import java.util.List;

import com.trangialam.dto.ContactRequest;
import com.trangialam.dto.LoginRequest;
import com.trangialam.dto.NewOrderRequest;
import com.trangialam.dto.PagingRequest;
import com.trangialam.dto.RegisterRequest;
import com.trangialam.dto.ResetPassWordRequest;
import com.trangialam.dto.UpdateUserRequest;
import com.trangialam.model.Category;
import com.trangialam.model.Item;
import com.trangialam.model.Order;
import com.trangialam.model.Product;
import com.trangialam.model.Role;
import com.trangialam.model.User;

public interface IServiceWeb {

	public boolean authenticate(LoginRequest loginRequest);
	
	public boolean register(RegisterRequest registerRequest);
	public User getUserInformation(String keyInformation);

	public List<Product> getProductsPaging(PagingRequest pagingRequest);

	public int getTotalPagesProduct(PagingRequest pagingRequest);

	public Product getProductById(int productId);
	
	public List<Category> getAllCategories();

	public List<Product> getProductCategoryPaging(PagingRequest pagingRequest, int categoryId);

	public int getTotalPagesProductCategory(PagingRequest pagingRequest);

	public List<Product> getProductSearchPaging(PagingRequest pagingRequest, String keyword);

	public int getTotalPagesProductSearch(PagingRequest pagingRequest);

	public boolean addOrderRequest(NewOrderRequest newOrderRequest);

	public List<Order> getOrdersPaging(PagingRequest pagingRequest, int userId);

	public int getTotalPagesOrderByUserId(PagingRequest pagingRequest);

	public List<Item> getItemsInOrder(int orderId);

	public Order getOrder(int orderId);

	public List<Role> getListRoles(User customer);

	public List<Product> getProductsHasPriceLower(BigDecimal price);

	public List<Product> getProductsByBrand(String brand);

	public List<Product> getProductsBrandPriceLower(BigDecimal price, String brand);

	public List<Product> getProductCategoryBrandPriceLower(int categoryId, BigDecimal price, String brand);

	public List<Product> getProductCategoryHasPriceLower(int categoryId, BigDecimal price);

	public List<Product> getProductCategoryByBrand(int categoryId, String brand);

	public boolean resetPassword(ResetPassWordRequest passwordRequest);

	public boolean updateUser(UpdateUserRequest updateUserRequest);

	public String resetCustomerPassword(String email);

	public boolean saveContactUser(ContactRequest contactRequest);

	
}
