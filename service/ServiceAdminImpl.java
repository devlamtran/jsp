package com.trangialam.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

public class ServiceAdminImpl implements IServiceAdmin {

	private IProductDAO productDAO;
	private ICategoryDAO categoryDAO;
	private IUserDAO userDAO;
	private IRoleDAO roleDAO;
	private IOrderDAO orderDAO;
	private IContactDAO contactDAO;

	public ServiceAdminImpl() {
		this.productDAO = new ProductDAOImpl();
		this.userDAO = new UserDAOImpl();
		this.categoryDAO = new CategoryDAOImpl();
		this.roleDAO = new RoleDAOImpl();
		this.orderDAO = new OrderDAOImpl();
		this.contactDAO = new ContactDAOImpl();
	}

	public boolean addProduct(AddProductRequest addProductRequest) {
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Product product = new Product();
		product.setUserId(addProductRequest.getUserId());
		product.setTitle(addProductRequest.getTitle());
		product.setLinkImage(addProductRequest.getLinkImage());
		product.setPrice(addProductRequest.getPrice());
		product.setDiscount(addProductRequest.getDiscount());
		product.setQuantity(addProductRequest.getQuantity());
		product.setBrand(addProductRequest.getBrand());
		product.setCreatedAt(LocalDateTime.now().format(myFormatObj));
		product.setContent(addProductRequest.getContent());
		return productDAO.saveProduct(product);
	}

	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return productDAO.getAllProducts();
	}

	public List<Product> getProductsPaging(PagingRequest pagingRequest) {

		int offset = (pagingRequest.getPageNumber() - 1) * pagingRequest.getRecordsPerPage();
		int records = pagingRequest.getRecordsPerPage();
		return productDAO.getProductsLimit(offset, records);
	}

	public int getTotalPagesProduct(PagingRequest pagingRequest) {
		// TODO Auto-generated method stub
		return (int) Math.ceil(productDAO.getTotalRecords() * 1.0 / pagingRequest.getRecordsPerPage());
	}

	public boolean editProduct(EditProductRequest editProductRequest) {
		// TODO Auto-generated method stub
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Product product = new Product();
		product.setId(editProductRequest.getId());
		product.setUserId(editProductRequest.getUserId());
		product.setTitle(editProductRequest.getTitle());
		product.setPrice(editProductRequest.getPrice());
		product.setDiscount(editProductRequest.getDiscount());
		product.setQuantity(editProductRequest.getQuantity());
		product.setBrand(editProductRequest.getBrand());
		product.setUpdatedAt(LocalDateTime.now().format(myFormatObj));
		product.setContent(editProductRequest.getContent());

		return productDAO.updateProduct(product);
	}

	public Product getProductById(int productId) {
		// TODO Auto-generated method stub
		return productDAO.getProductById(productId);
	}

	public boolean deleteProduct(DeleteProductRequest deleteProductRequest) {
		// TODO Auto-generated method stub
		List<Integer> listCategoriesIdAssigned = getAllProductCategoryIdByProductId(
				deleteProductRequest.getProductId());
		if (listCategoriesIdAssigned.size() == 0) {
			return productDAO.deleteProductById(deleteProductRequest.getProductId());
		} else {
			if (categoryDAO.deleteProductCategoryByProductId(deleteProductRequest.getProductId())) {
				return productDAO.deleteProductById(deleteProductRequest.getProductId());
			} else {
				return false;
			}
		}

	}

	public List<User> getUsersPaging(PagingRequest pagingRequest) {

		int offset = (pagingRequest.getPageNumber() - 1) * pagingRequest.getRecordsPerPage();
		int records = pagingRequest.getRecordsPerPage();
		return userDAO.getUsersLimit(offset, records);
	}

	public int getTotalPagesUser(PagingRequest pagingRequest) {
		// TODO Auto-generated method stub
		return (int) Math.ceil(userDAO.getTotalRecords() * 1.0 / pagingRequest.getRecordsPerPage());

	}

	public boolean addCategory(AddCategoryRequest addCategoryRequest) {
		// TODO Auto-generated method stub

		Category category = new Category();

		category.setTitle(addCategoryRequest.getTitle());

		category.setContent(addCategoryRequest.getContent());
		return categoryDAO.saveCategory(category);
	}

	public List<Category> getCategoriesPaging(PagingRequest pagingRequest) {
		// TODO Auto-generated method stub
		int offset = (pagingRequest.getPageNumber() - 1) * pagingRequest.getRecordsPerPage();
		int records = pagingRequest.getRecordsPerPage();
		return categoryDAO.getCategoriesLimit(offset, records);
	}

	public int getTotalPagesCategory(PagingRequest pagingRequest) {
		// TODO Auto-generated method stub
		return (int) Math.ceil(categoryDAO.getTotalRecords() * 1.0 / pagingRequest.getRecordsPerPage());

	}

	public List<Category> getAllCategory() {
		// TODO Auto-generated method stub
		return categoryDAO.getAllCategories();
	}

	public boolean assignProduct(AssignProductRequest assignProductRequest) {
		int productId = assignProductRequest.getProductId();
		List<Integer> listCategoriesId = assignProductRequest.getListCategoriesIdAssign();
		List<Integer> listCategoriesIdAssigned = getAllProductCategoryIdByProductId(productId);
		List<Integer> listCategoriesIdUnAssign = new ArrayList<Integer>();
		List<Integer> listCategoriesIdAssignUpdate = new ArrayList<Integer>();
		List<Integer> listCategoriesIdAssignDelete = new ArrayList<Integer>();
		if (listCategoriesIdAssigned.size() == 0) {
			listCategoriesIdUnAssign = listCategoriesId;
		} else {

			for (Integer idCategory : listCategoriesId) {
				for (Integer idCategoryAssigned : listCategoriesIdAssigned) {
					if (idCategory != idCategoryAssigned) {
						listCategoriesIdUnAssign.add(idCategory);
					} else {
						listCategoriesIdAssignUpdate.add(idCategory);
					}
				}
				for (Integer idCategoryAssigned : listCategoriesIdAssigned) {
					if (!listCategoriesId.contains(idCategoryAssigned)) {
						listCategoriesIdAssignDelete.add(idCategoryAssigned);
					}
				}
			}
		}
		if (listCategoriesIdAssigned.size() == 0) {
			return categoryDAO.insertProductCategory(productId, listCategoriesIdUnAssign);
		} 
		if ((listCategoriesIdAssignUpdate.size() > 0) && (listCategoriesIdAssignDelete.size() == 0)) {
			if (listCategoriesIdUnAssign.size()>0) {
				return categoryDAO.insertProductCategory(productId, listCategoriesIdUnAssign);
			} else {
				return true;
			}
			
		}
		if ((listCategoriesIdAssignUpdate.size() > 0) && (listCategoriesIdAssignDelete.size() > 0)) {
			return categoryDAO.deleteProductCategory(productId, listCategoriesIdAssignDelete);
		}
		if ((listCategoriesIdAssignUpdate.size() == 0) && (listCategoriesIdAssignDelete.size() > 0)) {
			if( categoryDAO.deleteProductCategory(productId, listCategoriesIdAssignDelete)){
				return categoryDAO.insertProductCategory(productId, listCategoriesIdUnAssign);
			};
		}
		return false;
		
				
		
					


	}

	public List<Integer> getAllProductCategoryIdByProductId(int productId) {

		return categoryDAO.getAllProductCategoryIdByProductId(productId);
	}

	public Category getCategoryById(int categoryId) {
		// TODO Auto-generated method stub
		return categoryDAO.getCategoryById(categoryId);
	}

	public boolean deleteCategory(DeleteCategoryRequest deleteCategoryRequest) {
		// TODO Auto-generated method stub
		List<Integer> listProductsIdAssigned = getAllProductCategoryIdByCategoryId(
				deleteCategoryRequest.getCategoryId());
		List<Integer> listCategoriesIdAssigned = getAllCategoryIdHasParentId(deleteCategoryRequest.getCategoryId());
		if ((listProductsIdAssigned.size() == 0) && (listCategoriesIdAssigned.size() == 0)) {
			return categoryDAO.deleteCategoryById(deleteCategoryRequest.getCategoryId());
		} else {
			if (listProductsIdAssigned.size() == 0) {
				if (categoryDAO.updateParentIdByCategoryId(listCategoriesIdAssigned)) {
					return categoryDAO.deleteCategoryById(deleteCategoryRequest.getCategoryId());
				} else {
					return false;
				}

			}
			if (categoryDAO.deleteProductCategoryByCategoryId(deleteCategoryRequest.getCategoryId())) {
				if (categoryDAO.updateParentIdByCategoryId(listCategoriesIdAssigned)) {
					return categoryDAO.deleteCategoryById(deleteCategoryRequest.getCategoryId());
				} else {
					return categoryDAO.deleteCategoryById(deleteCategoryRequest.getCategoryId());
				}

			} else {
				return false;
			}
		}
	}

	private List<Integer> getAllCategoryIdHasParentId(int categoryId) {
		// TODO Auto-generated method stub
		return categoryDAO.getAllCategoryIdHasParentId(categoryId);
	}

	public List<Integer> getAllProductCategoryIdByCategoryId(int categoryId) {
		// TODO Auto-generated method stub
		return categoryDAO.getAllProductCategoryIdByCategoryId(categoryId);
	}

	public boolean editCategory(EditCategoryRequest editCategoryRequest) {
		// TODO Auto-generated method stub
		Category category = new Category();
		category.setId(editCategoryRequest.getId());

		category.setTitle(editCategoryRequest.getTitle());

		category.setContent(editCategoryRequest.getContent());

		return categoryDAO.updateCategory(category);
	}

	public List<Integer> getAllCategoryParentIdByCategoryId(int categoryId) {
		// TODO Auto-generated method stub
		return categoryDAO.getAllCategoryParentIdByCategoryId(categoryId);
	}

	public boolean assignCategory(AssignCategoryRequest assignCategoryRequest) {
		// TODO Auto-generated method stub
		int categoryId = assignCategoryRequest.getCategoryId();
		List<Integer> listCategoriesId = assignCategoryRequest.getListCategoriesIdAssign();
		int parentIdCategory = listCategoriesId.get(0);

		Category category = new Category();
		category.setId(categoryId);
		category.setCategory(categoryDAO.getCategoryById(parentIdCategory));
		return categoryDAO.updateParentIdCategory(category);

	}

	public boolean addRole(AddRoleRequest addRoleRequest) {
		// TODO Auto-generated method stub
		Role role = new Role();

		role.setName(addRoleRequest.getName());

		role.setDescription(addRoleRequest.getDescription());
		return roleDAO.saveRole(role);
	}

	public List<Role> getAllRole() {
		// TODO Auto-generated method stub
		return roleDAO.getAllRoles();
	}

	public Role getRoleById(int roleId) {
		// TODO Auto-generated method stub
		return roleDAO.getRoleById(roleId);
	}

	public boolean deleteRole(DeleteRoleRequest deleteRoleRequest) {
		// TODO Auto-generated method stub
		List<Integer> listUsersIdAssigned = getAllUserRoleIdByRoleId(deleteRoleRequest.getRoleId());
		if (listUsersIdAssigned.size() == 0) {
			return roleDAO.deleteRoleById(deleteRoleRequest.getRoleId());
		} else {
			if (roleDAO.deleteUserRoleByRoleId(deleteRoleRequest.getRoleId())) {
				return roleDAO.deleteRoleById(deleteRoleRequest.getRoleId());
			} else {
				return false;
			}

		}

	}

	

	private List<Integer> getAllUserRoleIdByRoleId(int roleId) {
		// TODO Auto-generated method stub
		return roleDAO.getAllUserRoleIdByRoleId(roleId);
	}

	public List<Integer> getAllUserRoleIdByUserId(int userId) {
		// TODO Auto-generated method stub
	    return roleDAO.getAllUserRoleIdByUserId(userId);
	}

	public User getUserById(int userId) {
		// TODO Auto-generated method stub
		return userDAO.getUserById(userId);
	}

	public boolean assignRole(AssignRoleRequest assignRoleRequest) {
		// TODO Auto-generated method stub
		int userId = assignRoleRequest.getUserId();
		List<Integer> listRolesId = assignRoleRequest.getListRolesIdAssign();
		List<Integer> listRolesIdAssigned = getAllUserRoleIdByUserId(userId);
		List<Integer> listRolesIdUnAssign = new ArrayList<Integer>();
		List<Integer> listRolesIdAssignUpdate = new ArrayList<Integer>();
		List<Integer> listRolesIdAssignDelete = new ArrayList<Integer>();
		if (listRolesIdAssigned.size() == 0) {
			listRolesIdUnAssign = listRolesId;
		} else {

			for (Integer idRole : listRolesId) {
				for (Integer idRoleAssigned : listRolesIdAssigned) {
					if (idRole != idRoleAssigned) {
						listRolesIdUnAssign.add(idRole);
					} else {
						listRolesIdAssignUpdate.add(idRole);
					}
				}
				for (Integer idRoleAssigned : listRolesIdAssigned) {
					if (!listRolesId.contains(idRoleAssigned)) {
						listRolesIdAssignDelete.add(idRoleAssigned);
					}
				}
			}
		}
		if (listRolesIdAssigned.size() == 0) {
			return roleDAO.insertUserRole(userId, listRolesIdUnAssign);
		} 
		if ((listRolesIdAssignUpdate.size() > 0) && (listRolesIdAssignDelete.size() == 0)) {
			if (listRolesIdUnAssign.size()>0) {
				return roleDAO.insertUserRole(userId, listRolesIdUnAssign);
			} else {
				return true;
			}
			
		}
		if ((listRolesIdAssignUpdate.size() > 0) && (listRolesIdAssignDelete.size() > 0)) {
			return roleDAO.deleteUserRole(userId, listRolesIdAssignDelete);
		}
		if ((listRolesIdAssignUpdate.size() == 0) && (listRolesIdAssignDelete.size() > 0)) {
			if( roleDAO.deleteUserRole(userId, listRolesIdAssignDelete)){
				return roleDAO.insertUserRole(userId, listRolesIdUnAssign);
			}
		}
		return false;
	}

	public boolean deleteUser(DeleteUserRequest deleteUserRequest) {
		// TODO Auto-generated method stub
		List<Integer> listRolesIdAssigned = getAllUserRoleIdByUserId(deleteUserRequest.getUserId());
		if (listRolesIdAssigned.size() == 0) {
			return userDAO.deleteUserById(deleteUserRequest.getUserId());
		} else {
			if (userDAO.deleteUserRoleByUserId(deleteUserRequest.getUserId())) {
				return userDAO.deleteUserById(deleteUserRequest.getUserId());
			} else {
				return false;
			}

		}
	}

	public List<Order> getAllOrdersPaging(PagingRequest pagingRequest) {
		// TODO Auto-generated method stub
		int offset =  (pagingRequest.getPageNumber()-1)* pagingRequest.getRecordsPerPage();
		int records = pagingRequest.getRecordsPerPage();
		return orderDAO.getAllOrderLimit(offset, records);
	}

	public int getTotalPagesOrder(PagingRequest pagingRequest) {
		// TODO Auto-generated method stub
		return (int)Math.ceil(orderDAO.getTotalRecords()*1.0/pagingRequest.getRecordsPerPage());
	}

	public List<Item> getItemsInOrder(int orderId) {
		// TODO Auto-generated method stub
		return orderDAO.getItemsByOrderId(orderId);
	}

	public boolean deleleOrder(DeleteOrderRequest deleteOrderRequest) {
		List<Integer> listOrderItemIdOrder = getAllOrderItemIdByOrderId(deleteOrderRequest.getOrderId());
		if (listOrderItemIdOrder.size()==0) {
			return orderDAO.deleteOrderById(deleteOrderRequest.getOrderId());
		}else {
			if (orderDAO.deleteOrderItemById(listOrderItemIdOrder)) {
				return orderDAO.deleteOrderById(deleteOrderRequest.getOrderId());
			}else {
				return false;
			}
		}
		
	}

	private List<Integer> getAllOrderItemIdByOrderId(int orderId) {
		// TODO Auto-generated method stub
		 return orderDAO.getAllOrderItemIdByOrderId(orderId);
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

	public boolean deleteUserRole(int userId) {
		return roleDAO.deleteUserRoleByUserId(userId);
		
	}

	public boolean deleteProductCategory(int productId) {
		// TODO Auto-generated method stub
		return categoryDAO.deleteProductCategoryByProductId(productId);
	}

	public List<Contact> getContactsPaging(PagingRequest pagingRequest) {
		// TODO Auto-generated method stub
		int offset = (pagingRequest.getPageNumber() - 1) * pagingRequest.getRecordsPerPage();
		int records = pagingRequest.getRecordsPerPage();
		return contactDAO.getContactsLimit(offset, records);
	}

	public int getTotalPagesContact(PagingRequest pagingRequest) {
		// TODO Auto-generated method stub
		return (int)Math.ceil( contactDAO.getTotalRecords()*1.0/pagingRequest.getRecordsPerPage());
	}

	public Contact getContactById(int contactId) {
		// TODO Auto-generated method stub
		return contactDAO.getContactById(contactId);
	}

	

}
