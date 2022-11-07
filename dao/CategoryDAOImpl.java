package com.trangialam.dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.trangialam.model.Category;
import com.trangialam.utilities.IDBManager;
import com.trangialam.utilities.MySQLDBManager;

public class CategoryDAOImpl implements ICategoryDAO {

	private IDBManager dbManager;
	private int totalRecords;

	public CategoryDAOImpl() {
		this.dbManager = new MySQLDBManager();
	}

	public List<Category> getAllCategories() {
		// TODO Auto-generated method stub
		List<Category> listCategories = new ArrayList<Category>();
		String sql = "SELECT * FROM category";

		try {
			Statement statement = dbManager.getConnection().createStatement();

			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String title = resultSet.getString("title");
				String content = resultSet.getString("content");

				Category category = new Category();
				category.setId(id);
				category.setTitle(title);
				category.setContent(content);
				listCategories.add(category);
			}

			dbManager.closeStatement(resultSet, statement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return listCategories;
	}

	public boolean saveCategory(Category category) {
		// TODO Auto-generated method stub
		int rowsInserted = 0;
		String sql = "INSERT INTO category (title,content) VALUES (?, ?)";

		try {
			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
			statement.setString(1, category.getTitle());

			statement.setString(2, category.getContent());

			rowsInserted = statement.executeUpdate();
			dbManager.closeStatement(statement);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("insert loi");
			return false;

		}
		if (rowsInserted > 0) {
			return true;
		} else {
			return false;
		}
	}

	public List<Category> getCategoriesLimit(int offset, int records) {
		List<Category> listCategories = new ArrayList<Category>();
		String sql = "select * from category limit " + offset + ", " + records;

		try {

			Statement statement = dbManager.getConnection().createStatement();

			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				int id = resultSet.getInt("id");

				String title = resultSet.getString("title");

				String content = resultSet.getString("content");

				Category category = new Category();
				category.setId(id);
				category.setTitle(title);

				category.setContent(content);
				// System.out.println(user.toString());
				listCategories.add(category);
			}
			dbManager.closeResultSet(resultSet);
			resultSet = statement.executeQuery("SELECT COUNT(*) FROM category");
			if (resultSet.next()) {
				this.setTotalRecords(resultSet.getInt(1));
			}
			dbManager.closeStatement(resultSet, statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listCategories;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public boolean insertProductCategory(int productId, List<Integer> listCategoriesId) {
		// TODO Auto-generated method stub
		int rowsInserted = 0;
		String sql = "INSERT INTO product_category (productId,categoryId) VALUES (?, ?)";
		for (Integer categoryId : listCategoriesId) {
			try {
				PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
				statement.setInt(1, productId);

				statement.setInt(2, categoryId);

				rowsInserted = statement.executeUpdate();
				dbManager.closeStatement(statement);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("insert loi");
				return false;

			}
		}

		if (rowsInserted > 0) {
			return true;
		} else {
			return false;
		}
	}

	public List<Integer> getAllProductCategoryIdByProductId(int productId) {
		// TODO Auto-generated method stub
		List<Integer> listCategoryIds = new ArrayList<Integer>();
		String sql = "SELECT * FROM product_category where productId =?";

		try {
			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
			statement.setInt(1, productId);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int idCategory = resultSet.getInt("categoryId");

				listCategoryIds.add(idCategory);
			}

			dbManager.closeStatement(resultSet, statement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return listCategoryIds;
	}

	public boolean updateProductCategory(int productId, List<Integer> listCategoriesIdAssignUpdate) {
		// TODO Auto-generated method stub
		int rowsUpdated = 0;
		String sql = "UPDATE product_category SET categoryId = ? WHERE productId = ?";
		for (Integer categoryId : listCategoriesIdAssignUpdate) {
			try {
				PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
				statement.setInt(1, categoryId);

				statement.setInt(2, productId);

				rowsUpdated = statement.executeUpdate();
				dbManager.closeStatement(statement);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("insert loi");
				return false;

			}
		}

		if (rowsUpdated >0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean deleteProductCategory(int productId, List<Integer> listCategoriesIdAssignDelete) {
		// TODO Auto-generated method stub
		int rowsDeleted = 0;
		String sql = "delete from  product_category where categoryId =? and productId=?";

		for (Integer categoryId : listCategoriesIdAssignDelete) {
			try {
				PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
				statement.setInt(1, categoryId);

				statement.setInt(2, productId);

				rowsDeleted += statement.executeUpdate();
				dbManager.closeStatement(statement);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("delete loi");
				return false;

			}
		}
		if (rowsDeleted == listCategoriesIdAssignDelete.size()) {
			return true;
		} else {
			return false;
		}

	}

	public boolean deleteProductCategoryByProductId(int productId) {
		// TODO Auto-generated method stub
		int rowsDeleted = 0;
		String sql = "delete from  product_category where productId=?";

		
			try {
				PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
				statement.setInt(1, productId);


				rowsDeleted = statement.executeUpdate();
				dbManager.closeStatement(statement);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("delete loi");
				return false;

			}
		
		if (rowsDeleted >0) {
			return true;
		} else {
			return false;
		}

	}

	public Category getCategoryById(int categoryId) {
		// TODO Auto-generated method stub
		Category category = null;
		String sql = "select * from  category where id =? ";

		try {

			PreparedStatement  statement = dbManager.getConnection().prepareStatement(sql);
			statement.setInt(1, categoryId);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String title = resultSet.getString("title");
				int parentId = resultSet.getInt("parentId");
				String content = resultSet.getString("content");

				category = new Category();
				category.setId(id);
				category.setTitle(title);
				category.setContent(content);
				category.setCategory(getCategoryById(parentId));
				
				// System.out.println(user.toString());

			}

			dbManager.closeStatement(resultSet, statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return category;
	}

	public boolean deleteCategoryById(int categoryId) {
		// TODO Auto-generated method stub
		int rowsDeleted = 0;
		String sql = "delete from category where id =? ";

		try {
			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
			statement.setInt(1, categoryId);

			rowsDeleted = statement.executeUpdate();
			dbManager.closeStatement(statement);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("delete loi");
			return false;

		}
		if (rowsDeleted > 0) {
			return true;
		} else {
			return false;
		}
	}

	public List<Integer> getAllProductCategoryIdByCategoryId(int categoryId) {
		// TODO Auto-generated method stub
		List<Integer> listProductIds = new ArrayList<Integer>();
		String sql = "SELECT * FROM product_category where categoryId =?";

		try {
			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
			statement.setInt(1, categoryId);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int idProduct = resultSet.getInt("productId");

				listProductIds.add(idProduct);
			}

			dbManager.closeStatement(resultSet, statement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return listProductIds;
	}

	public boolean deleteProductCategoryByCategoryId(int categoryId) {
		// TODO Auto-generated method stub
		int rowsDeleted = 0;
		String sql = "delete from  product_category where categoryId=?";

		
			try {
				PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
				statement.setInt(1, categoryId);


				rowsDeleted = statement.executeUpdate();
				dbManager.closeStatement(statement);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("delete loi");
				return false;

			}
		
		if (rowsDeleted >0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean updateCategory(Category category) {
		// TODO Auto-generated method stub
		int rowsUpdated = 0;
		String sql = "UPDATE category SET title =?,content =? WHERE id =?";

		try {
			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);

			statement.setString(1, category.getTitle());
			
			statement.setString(2, category.getContent());
			
			statement.setInt(3,category.getId());
			rowsUpdated = statement.executeUpdate();
			dbManager.closeStatement(statement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		if (rowsUpdated > 0) {
			return true;
		} else {
			return false;
		}
	}

	public List<Integer> getAllCategoryParentIdByCategoryId(int categoryId) {
		// TODO Auto-generated method stub
		List<Integer> listCategoryIds = new ArrayList<Integer>();
		String sql = "SELECT * FROM category where id =?";

		try {
			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
			statement.setInt(1, categoryId);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int parentId = resultSet.getInt("parentId");

				listCategoryIds.add(parentId);
			}

			dbManager.closeStatement(resultSet, statement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return listCategoryIds;
	}

	public boolean updateParentIdCategory(Category category) {
		// TODO Auto-generated method stub
		int rowsUpdated = 0;
		String sql = "UPDATE category SET parentId =? WHERE id =?";

		try {
			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);

			statement.setInt(1, category.getCategory().getId());
			
			statement.setInt(2, category.getId());
			
			
			rowsUpdated = statement.executeUpdate();
			dbManager.closeStatement(statement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		if (rowsUpdated > 0) {
			return true;
		} else {
			return false;
		}
	}

	public List<Integer> getAllCategoryIdHasParentId(int categoryId) {
		// TODO Auto-generated method stub
		List<Integer> listCategoryIds = new ArrayList<Integer>();
		String sql = "SELECT * FROM category where parentId =?";

		try {
			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
			statement.setInt(1, categoryId);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");

				listCategoryIds.add(id);
			}

			dbManager.closeStatement(resultSet, statement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return listCategoryIds;
	}

	public boolean updateParentIdByCategoryId(int categoryId) {
		// TODO Auto-generated method stub
		int rowsUpdated = 0;
		String sql = "UPDATE category SET parentId = null WHERE id =?";

		
			try {
				PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);

				
				statement.setInt(1, categoryId);	
				rowsUpdated = statement.executeUpdate();
				dbManager.closeStatement(statement);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		
		

		if (rowsUpdated >0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean updateParentIdByCategoryId(List<Integer> listCategoriesIdAssigned) {
		// TODO Auto-generated method stub
		int rowsUpdated = 0;
		String sql = "UPDATE category SET parentId = null WHERE id =?";

		for (Integer categoryId : listCategoriesIdAssigned) {
			
		
			try {
				PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);

				
				statement.setInt(1, categoryId);	
				rowsUpdated = statement.executeUpdate();
				dbManager.closeStatement(statement);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		
		}

		if (rowsUpdated >0) {
			return true;
		} else {
			return false;
		}
	}

}
