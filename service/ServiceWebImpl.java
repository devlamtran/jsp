package com.trangialam.service;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

import com.trangialam.dao.CategoryDAOImpl;
import com.trangialam.dao.ContactDAOImpl;
import com.trangialam.dao.ICategoryDAO;
import com.trangialam.dao.IContactDAO;
import com.trangialam.dao.IOrderDAO;
import com.trangialam.dao.IProductDAO;
import com.trangialam.dao.IRoleDAO;
import com.trangialam.dao.IUserDAO;
import com.trangialam.dao.OrderDAOImpl;
import com.trangialam.dao.ProductDAOImpl;
import com.trangialam.dao.RoleDAOImpl;
import com.trangialam.dao.UserDAOImpl;
import com.trangialam.dto.ContactRequest;
import com.trangialam.dto.LoginRequest;
import com.trangialam.dto.NewOrderRequest;
import com.trangialam.dto.PagingRequest;
import com.trangialam.dto.RegisterRequest;
import com.trangialam.dto.ResetPassWordRequest;
import com.trangialam.dto.UpdateUserRequest;
import com.trangialam.model.Category;
import com.trangialam.model.Contact;
import com.trangialam.model.Item;
import com.trangialam.model.Order;
import com.trangialam.model.Product;
import com.trangialam.model.Role;
import com.trangialam.model.User;


public class ServiceWebImpl implements IServiceWeb {
	
	private IProductDAO productDAO;
	private IUserDAO userDAO;
	private ICategoryDAO categoryDAO;
	private IOrderDAO orderDAO;
	private IRoleDAO roleDAO;
	private IContactDAO contactDAO;
	
	public ServiceWebImpl() {
	
		this.userDAO = new UserDAOImpl();
		this.productDAO = new ProductDAOImpl();
		this.categoryDAO = new CategoryDAOImpl();
		this.orderDAO = new OrderDAOImpl();
		this.roleDAO = new RoleDAOImpl();
		this.contactDAO = new ContactDAOImpl();
	}

	public boolean authenticate(LoginRequest loginRequest) {
		User user = new User();
		
		user.setEmail(loginRequest.getEmail());
		user.setMobile(loginRequest.getMobile());
		user.setUserName(loginRequest.getUsername());
		user.setPassword(loginRequest.getPassword());
		
		return userDAO.checkPasswordUser(user);
	}

	public boolean register(RegisterRequest registerRequest)  {
		// TODO Auto-generated method stub
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		User user = new User();
		user.setFirstName(registerRequest.getFirstName());
		user.setMiddleName(registerRequest.getMiddleName());
		user.setLastName(registerRequest.getLastName());
		user.setEmail(registerRequest.getEmail());
		user.setMobile(registerRequest.getMobile());
		user.setUserName(registerRequest.getUserName());
		user.setPassword(registerRequest.getPassword());
		user.setRegisterDay(LocalDateTime.now().format(myFormatObj));
		Role role = roleDAO.getRoleByName("USER");
		
	    if (userDAO.saveUser(user)) {
		  return userDAO.saveUserRole(getUserInformation(user.getMobile()),role);	
		}
	    return false;
	}

	public User getUserInformation(String keyInformation) {
		// TODO Auto-generated method stub
		User customer = userDAO.getUserAfterLogin(keyInformation);
		customer.setListRole(roleDAO.getAllRolesByUserId(customer.getId()));
		return customer;
	}

	public List<Product> getProductsPaging(PagingRequest pagingRequest) {
		// TODO Auto-generated method stub
		int offset =  (pagingRequest.getPageNumber()-1)* pagingRequest.getRecordsPerPage();
		int records = pagingRequest.getRecordsPerPage();
		return productDAO.getProductsLimit(offset, records);
	}

	public int getTotalPagesProduct(PagingRequest pagingRequest) {
		// TODO Auto-generated method stub
		return (int)Math.ceil( productDAO.getTotalRecords()*1.0/pagingRequest.getRecordsPerPage());

	}

	public Product getProductById(int productId) {
		// TODO Auto-generated method stub
		return productDAO.getProductById(productId);
	}

	public List<Category> getAllCategories() {
		// TODO Auto-generated method stub
		return  categoryDAO.getAllCategories();
	}

	public List<Product> getProductCategoryPaging(PagingRequest pagingRequest, int categoryId) {
		// TODO Auto-generated method stub
		int offset =  (pagingRequest.getPageNumber()-1)* pagingRequest.getRecordsPerPage();
		int records = pagingRequest.getRecordsPerPage();
		return productDAO.getProductCategoryLimit(offset, records,categoryId);
	}

	public int getTotalPagesProductCategory(PagingRequest pagingRequest) {
		// TODO Auto-generated method stub
		return (int)Math.ceil( productDAO.getTotalRecords()*1.0/pagingRequest.getRecordsPerPage());

	}

	public List<Product> getProductSearchPaging(PagingRequest pagingRequest, String keyword) {
		// TODO Auto-generated method stub
		int offset =  (pagingRequest.getPageNumber()-1)* pagingRequest.getRecordsPerPage();
		int records = pagingRequest.getRecordsPerPage();
		return productDAO.getProductSearchLimit(offset, records,keyword);
	}

	public int getTotalPagesProductSearch(PagingRequest pagingRequest) {
		// TODO Auto-generated method stub
		return (int)Math.ceil( productDAO.getTotalRecords()*1.0/pagingRequest.getRecordsPerPage());
	}

	public boolean addOrderRequest(NewOrderRequest newOrderRequest) {
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Order order = new Order();
		order.setFirstName(newOrderRequest.getFirstName());
		order.setLastName(newOrderRequest.getLastName());
		order.setMiddleName(newOrderRequest.getMiddleName());
		order.setMobile(newOrderRequest.getMobile());
		order.setEmail(newOrderRequest.getEmail());
		order.setCity(newOrderRequest.getCity());
		order.setProvince(newOrderRequest.getProvince());
		order.setCountry(newOrderRequest.getCountry());
		order.setContent(newOrderRequest.getContent());
		order.setSessionId(newOrderRequest.getSessionId());
		order.setToken(newOrderRequest.getToken());
		order.setCreatedAt(LocalDateTime.now().format(myFormatObj));
		order.setCustomer(newOrderRequest.getCustomer());
		order.setPrice(newOrderRequest.getOrder().getPrice());
		
		if(orderDAO.saveOrder(order)) {
			return orderDAO.saveOrderItems(newOrderRequest.getOrder().getItems(),order.getSessionId());
		}
		return false;
	}

	public List<Order> getOrdersPaging(PagingRequest pagingRequest, int userId) {
		// TODO Auto-generated method stub
		int offset =  (pagingRequest.getPageNumber()-1)* pagingRequest.getRecordsPerPage();
		int records = pagingRequest.getRecordsPerPage();
		return orderDAO.getOrderByUserIdLimit(offset, records,userId);
	}

	public int getTotalPagesOrderByUserId(PagingRequest pagingRequest) {
		// TODO Auto-generated method stub
		return (int)Math.ceil(orderDAO.getTotalRecords()*1.0/pagingRequest.getRecordsPerPage());

	}

	public List<Item> getItemsInOrder(int orderId) {
		
		return orderDAO.getItemsByOrderId(orderId);
	}

	public Order getOrder(int orderId) {
		Order order = orderDAO.getOrderById(orderId);
		order.setItems(getItemsInOrder(orderId));
		return order;
	}

	public List<Role> getListRoles(User customer) {
		// TODO Auto-generated method stub
		return userDAO.getListRolesByUserId(customer.getId());
	}

	public List<Product> getProductsHasPriceLower(BigDecimal price) {
		// TODO Auto-generated method stub
		return productDAO.getProductsByPrice(price);
	}

	public List<Product> getProductsByBrand(String brand) {
		// TODO Auto-generated method stub
		return productDAO.getProductsByBrand(brand);
	}

	public List<Product> getProductsBrandPriceLower(BigDecimal price, String brand) {
		
		if(price.doubleValue()==0 && !brand.equalsIgnoreCase("all")) {
			getProductsByBrand(brand);
		}
		if(price.doubleValue()!=0 && brand.equalsIgnoreCase("all")) {
			 getProductsHasPriceLower(price);
		}
		return productDAO.getProductsByBrandPrice(price,brand);
	}

	public List<Product> getProductCategoryBrandPriceLower(int categoryId, BigDecimal price, String brand) {
		// TODO Auto-generated method stub
		return productDAO.getProductCategoryByBrandPrice(categoryId,price,brand);
	}

	public List<Product> getProductCategoryHasPriceLower(int categoryId, BigDecimal price) {
		// TODO Auto-generated method stub
		return productDAO.getProductCategoryByPrice(categoryId,price);
	}

	public List<Product> getProductCategoryByBrand(int categoryId, String brand) {
		// TODO Auto-generated method stub
		return productDAO.getProductCategoryByBrand(categoryId,brand);
	}

	public boolean resetPassword(ResetPassWordRequest passwordRequest) {
		// TODO Auto-generated method stub
		User user = new User();
		
		user.setUserName(passwordRequest.getUserName());
		user.setPassword(passwordRequest.getPassword());
		
	    if (userDAO.updateUser(user)) {
		  return true;
		}
	    return false;
	}

	public boolean updateUser(UpdateUserRequest updateUserRequest) {
		// TODO Auto-generated method stub
        User user = new User();
		
        user.setId(updateUserRequest.getId());
		user.setUserName(updateUserRequest.getUserName());
		user.setEmail(updateUserRequest.getEmail());
		user.setMobile(updateUserRequest.getMobile());
		user.setFirstName(updateUserRequest.getFirstName());
		user.setLastName(updateUserRequest.getLastName());
		user.setMiddleName(updateUserRequest.getMiddleName());
		user.setLinkImage(updateUserRequest.getLinkImage());
	    if (userDAO.updateUserProfile(user)) {
		  return true;
		}
	    return false;
	}

	public String resetCustomerPassword(String email) {
		// TODO Auto-generated method stub
		User user = userDAO.getUserAfterLogin(email);
	     
	    String randomPassword = RandomStringUtils.randomAlphanumeric(10);
	     
	    user.setPassword(randomPassword);
	    userDAO.updateUser(user);
	     
	    return randomPassword;
	}

	public boolean saveContactUser(ContactRequest contactRequest) {
		// TODO Auto-generated method stub
        Contact contact = new Contact();
		
		contact.setName(contactRequest.getName());
		contact.setEmail(contactRequest.getEmail());
		contact.setSubject(contactRequest.getSubject());
		contact.setMessage(contactRequest.getMessage());
	    if (contactDAO.saveContact(contact)) {
		  return true;
		}
	    return false;
	}

	
}
