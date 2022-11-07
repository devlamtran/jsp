package com.trangialam.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.trangialam.model.Product;
import com.trangialam.utilities.IDBManager;
import com.trangialam.utilities.MySQLDBManager;

public class ProductDAOImpl implements IProductDAO {
	private IDBManager dbManager;
	private int totalRecords;

	public ProductDAOImpl() {

		this.dbManager = new MySQLDBManager();
	}

	public boolean saveProduct(Product product) {
		// TODO Auto-generated method stub
		int rowsInserted = 0;
		String sql = "INSERT INTO product (userId,title,linkImage,price,discount,quantity,brand,createdAt,content) VALUES (?, ?, ?, ? , ? , ? , ? , ? ,?)";

		try {
			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
			statement.setInt(1, product.getUserId());
			statement.setString(2, product.getTitle());
			statement.setString(3, product.getLinkImage());
			statement.setBigDecimal(4, product.getPrice());
			statement.setBigDecimal(5, product.getDiscount());
			statement.setInt(6, product.getQuantity());
			statement.setString(7, product.getBrand());
			statement.setString(8, product.getCreatedAt());
			statement.setString(9, product.getContent());

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

	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		List<Product> listProduct = new ArrayList<Product>();
		String sql = "SELECT * FROM product";

		try {
			Statement statement = dbManager.getConnection().createStatement();

			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String title = resultSet.getString("title");
				String linkImage = resultSet.getString("linkImage");
				BigDecimal price = resultSet.getBigDecimal("price");
				BigDecimal discount = resultSet.getBigDecimal("discount");
				int quantity = resultSet.getInt("quantity");
				String brand = resultSet.getString("brand");
				String createdAt = resultSet.getString("createdAt");
				String content = resultSet.getString("content");

				Product product = new Product();
				product.setId(id);
				product.setTitle(title);
				product.setLinkImage(linkImage);
				product.setPrice(price);
				product.setDiscount(discount);
				product.setQuantity(quantity);
				product.setBrand(brand);
				product.setCreatedAt(createdAt);
				product.setContent(content);
				// System.out.println(user.toString());
				listProduct.add(product);
			}

			dbManager.closeStatement(resultSet, statement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return listProduct;
		}

		return listProduct;
	}

	public List<Product> getProductsLimit(int offset, int records) {
		// TODO Auto-generated method stub
		List<Product> listProduct = new ArrayList<Product>();
		String sql = "select * from product limit " + offset + ", " + records;

		try {

			Statement statement = dbManager.getConnection().createStatement();

			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String title = resultSet.getString("title");
				String linkImage = resultSet.getString("linkImage");
				BigDecimal price = resultSet.getBigDecimal("price");
				BigDecimal discount = resultSet.getBigDecimal("discount");
				int quantity = resultSet.getInt("quantity");
				String brand = resultSet.getString("brand");
				String createdAt = resultSet.getString("createdAt");
				String content = resultSet.getString("content");

				Product product = new Product();
				product.setId(id);
				product.setTitle(title);
				product.setLinkImage(linkImage);
				product.setPrice(price);
				product.setDiscount(discount);
				product.setQuantity(quantity);
				product.setBrand(brand);
				product.setCreatedAt(createdAt);
				product.setContent(content);
				// System.out.println(user.toString());
				listProduct.add(product);
			}
			dbManager.closeResultSet(resultSet);
			resultSet = statement.executeQuery("SELECT COUNT(*) FROM product");
			if (resultSet.next()) {
				this.setTotalRecords(resultSet.getInt(1));
			}
			dbManager.closeStatement(resultSet, statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listProduct;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public boolean updateProduct(Product product) {
		int rowsUpdated = 0;
		String sql = "UPDATE product SET title = ?, price = ?, discount = ?, quantity = ?, brand = ?, updatedAt = ?, content = ?, userId = ? WHERE id = ?";

		try {
			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);

			statement.setString(1, product.getTitle());
			statement.setBigDecimal(2, product.getPrice());
			statement.setBigDecimal(3, product.getDiscount());
			statement.setInt(4, product.getQuantity());
			statement.setString(5, product.getBrand());
			statement.setString(6, product.getUpdatedAt());
			statement.setString(7, product.getContent());
			statement.setInt(8, product.getUserId());
			statement.setInt(9, product.getId());
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

	public Product getProductById(int productId) {
		Product product = null;
		String sql = "select * from product where id = " + productId;

		try {

			Statement statement = dbManager.getConnection().createStatement();

			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String title = resultSet.getString("title");
				String linkImage = resultSet.getString("linkImage");
				BigDecimal price = resultSet.getBigDecimal("price");
				BigDecimal discount = resultSet.getBigDecimal("discount");
				int quantity = resultSet.getInt("quantity");
				String brand = resultSet.getString("brand");
				String createdAt = resultSet.getString("createdAt");
				String content = resultSet.getString("content");

				product = new Product();
				product.setId(id);
				product.setTitle(title);
				product.setLinkImage(linkImage);
				product.setPrice(price);
				product.setDiscount(discount);
				product.setQuantity(quantity);
				product.setBrand(brand);
				product.setCreatedAt(createdAt);
				product.setContent(content);
				// System.out.println(user.toString());

			}

			dbManager.closeStatement(resultSet, statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return product;
	}

	public boolean deleteProductById(int productId) {
		int rowsDeleted = 0;
		String sql = "delete from product where id =? ";

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
		if (rowsDeleted > 0) {
			return true;
		} else {
			return false;
		}

	}

	public List<Product> getProductCategoryLimit(int offset, int records, int categoryId) {
		// TODO Auto-generated method stub
		List<Product> listProduct = new ArrayList<Product>();
		String sql = "select * from product,product_category where product.id = product_category.productId and product_category.categoryId = "+categoryId + " limit " + offset + "," + records;

		try {

			Statement statement = dbManager.getConnection().createStatement();

			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String title = resultSet.getString("title");
				String linkImage = resultSet.getString("linkImage");
				BigDecimal price = resultSet.getBigDecimal("price");
				BigDecimal discount = resultSet.getBigDecimal("discount");
				int quantity = resultSet.getInt("quantity");
				String brand = resultSet.getString("brand");
				String createdAt = resultSet.getString("createdAt");
				String content = resultSet.getString("content");

				Product product = new Product();
				product.setId(id);
				product.setTitle(title);
				product.setLinkImage(linkImage);
				product.setPrice(price);
				product.setDiscount(discount);
				product.setQuantity(quantity);
				product.setBrand(brand);
				product.setCreatedAt(createdAt);
				product.setContent(content);
				// System.out.println(user.toString());
				listProduct.add(product);
			}
			dbManager.closeResultSet(resultSet);
			resultSet = statement.executeQuery("SELECT COUNT(*) FROM product,product_category where product.id = product_category.productId and product_category.categoryId ="  + categoryId);
			if (resultSet.next()) {
				this.setTotalRecords(resultSet.getInt(1));
			}
			dbManager.closeStatement(resultSet, statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listProduct;
	}

	public List<Product> getProductSearchLimit(int offset, int records, String keyword) {
		// TODO Auto-generated method stub
		List<Product> listProduct = new ArrayList<Product>();
		String sql = "select * from product where title like ? limit ?, ?";

		try {

			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
			statement.setString(1, "%" +keyword+"%");
			statement.setInt(2, offset);
			statement.setInt(3, records);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String title = resultSet.getString("title");
				String linkImage = resultSet.getString("linkImage");
				BigDecimal price = resultSet.getBigDecimal("price");
				BigDecimal discount = resultSet.getBigDecimal("discount");
				int quantity = resultSet.getInt("quantity");
				String brand = resultSet.getString("brand");
				String createdAt = resultSet.getString("createdAt");
				String content = resultSet.getString("content");

				Product product = new Product();
				product.setId(id);
				product.setTitle(title);
				product.setLinkImage(linkImage);
				product.setPrice(price);
				product.setDiscount(discount);
				product.setQuantity(quantity);
				product.setBrand(brand);
				product.setCreatedAt(createdAt);
				product.setContent(content);
				// System.out.println(user.toString());
				listProduct.add(product);
			}
			dbManager.closeResultSet(resultSet);
			resultSet = statement.executeQuery("SELECT COUNT(*) FROM product where title like '%"  + keyword +"%'");
			if (resultSet.next()) {
				this.setTotalRecords(resultSet.getInt(1));
			}
			dbManager.closeStatement(resultSet, statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listProduct;
	}

	public List<Product> getProductsByPrice(BigDecimal priceCompair) {
		List<Product> listProduct = new ArrayList<Product>();
		String sql = "select * from product where price <=?";

		try {

			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
			
			statement.setBigDecimal(1, priceCompair);;
			
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String title = resultSet.getString("title");
				String linkImage = resultSet.getString("linkImage");
				BigDecimal price = resultSet.getBigDecimal("price");
				BigDecimal discount = resultSet.getBigDecimal("discount");
				int quantity = resultSet.getInt("quantity");
				String brand = resultSet.getString("brand");
				String createdAt = resultSet.getString("createdAt");
				String content = resultSet.getString("content");

				Product product = new Product();
				product.setId(id);
				product.setTitle(title);
				product.setLinkImage(linkImage);
				product.setPrice(price);
				product.setDiscount(discount);
				product.setQuantity(quantity);
				product.setBrand(brand);
				product.setCreatedAt(createdAt);
				product.setContent(content);
				// System.out.println(user.toString());
				listProduct.add(product);
			}
			dbManager.closeResultSet(resultSet);
			resultSet = statement.executeQuery("SELECT COUNT(*) FROM product where price <="  + priceCompair);
			if (resultSet.next()) {
				this.setTotalRecords(resultSet.getInt(1));
			}
			dbManager.closeStatement(resultSet, statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listProduct;
		
	}

	public List<Product> getProductsByBrand(String brandCompair) {
		// TODO Auto-generated method stub
		List<Product> listProduct = new ArrayList<Product>();
		String sql = "select * from product where brand like ?";

		try {

			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
			
			statement.setString(1, "%" +brandCompair+"%");
			
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String title = resultSet.getString("title");
				String linkImage = resultSet.getString("linkImage");
				BigDecimal price = resultSet.getBigDecimal("price");
				BigDecimal discount = resultSet.getBigDecimal("discount");
				int quantity = resultSet.getInt("quantity");
				String brand = resultSet.getString("brand");
				String createdAt = resultSet.getString("createdAt");
				String content = resultSet.getString("content");

				Product product = new Product();
				product.setId(id);
				product.setTitle(title);
				product.setLinkImage(linkImage);
				product.setPrice(price);
				product.setDiscount(discount);
				product.setQuantity(quantity);
				product.setBrand(brand);
				product.setCreatedAt(createdAt);
				product.setContent(content);
				// System.out.println(user.toString());
				listProduct.add(product);
			}
			dbManager.closeResultSet(resultSet);
			resultSet = statement.executeQuery("SELECT COUNT(*) FROM product where brand like '%"  + brandCompair + "%'");
			if (resultSet.next()) {
				this.setTotalRecords(resultSet.getInt(1));
			}
			dbManager.closeStatement(resultSet, statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listProduct;
	}

	public List<Product> getProductsByBrandPrice(BigDecimal priceCompair, String brandCompair) {
		
		List<Product> listProduct = new ArrayList<Product>();
		String sql = "select * from product where brand like ? and price <=?";

		try {

			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
			
			statement.setString(1, "%" +brandCompair+"%");
			statement.setBigDecimal(2, priceCompair);
			
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String title = resultSet.getString("title");
				String linkImage = resultSet.getString("linkImage");
				BigDecimal price = resultSet.getBigDecimal("price");
				BigDecimal discount = resultSet.getBigDecimal("discount");
				int quantity = resultSet.getInt("quantity");
				String brand = resultSet.getString("brand");
				String createdAt = resultSet.getString("createdAt");
				String content = resultSet.getString("content");

				Product product = new Product();
				product.setId(id);
				product.setTitle(title);
				product.setLinkImage(linkImage);
				product.setPrice(price);
				product.setDiscount(discount);
				product.setQuantity(quantity);
				product.setBrand(brand);
				product.setCreatedAt(createdAt);
				product.setContent(content);
				// System.out.println(user.toString());
				listProduct.add(product);
			}
			dbManager.closeResultSet(resultSet);
			resultSet = statement.executeQuery("SELECT COUNT(*) FROM product where brand like '%"  + brandCompair + "%' and price <=" + priceCompair);
			if (resultSet.next()) {
				this.setTotalRecords(resultSet.getInt(1));
			}
			dbManager.closeStatement(resultSet, statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listProduct;
	}

	public List<Product> getProductCategoryByBrandPrice(int categoryId, BigDecimal priceCompair, String brandCompair) {
		// TODO Auto-generated method stub
		List<Product> listProduct = new ArrayList<Product>();
		String sql = "select * from product,product_category where product.brand like ? and product.price <=? and product_category.productId = product.productId and product_category.categoryId=? ";

		try {

			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
			
			statement.setString(1, "%" +brandCompair+"%");
			statement.setBigDecimal(2, priceCompair);
			statement.setInt(3, categoryId);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String title = resultSet.getString("title");
				String linkImage = resultSet.getString("linkImage");
				BigDecimal price = resultSet.getBigDecimal("price");
				BigDecimal discount = resultSet.getBigDecimal("discount");
				int quantity = resultSet.getInt("quantity");
				String brand = resultSet.getString("brand");
				String createdAt = resultSet.getString("createdAt");
				String content = resultSet.getString("content");

				Product product = new Product();
				product.setId(id);
				product.setTitle(title);
				product.setLinkImage(linkImage);
				product.setPrice(price);
				product.setDiscount(discount);
				product.setQuantity(quantity);
				product.setBrand(brand);
				product.setCreatedAt(createdAt);
				product.setContent(content);
				// System.out.println(user.toString());
				listProduct.add(product);
			}
			dbManager.closeResultSet(resultSet);
			resultSet = statement.executeQuery("SELECT COUNT(*) FROM product,product_category where product.brand like '%"  + brandCompair + "%' and product.price <=" + priceCompair +" and product_category.productId = product.productId and product_category.categoryId=" + categoryId);
			if (resultSet.next()) {
				this.setTotalRecords(resultSet.getInt(1));
			}
			dbManager.closeStatement(resultSet, statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listProduct;
	}

	public List<Product> getProductCategoryByPrice(int categoryId, BigDecimal priceCompair) {
		// TODO Auto-generated method stub
		List<Product> listProduct = new ArrayList<Product>();
		String sql = "select * from product,product_category where product.price <=? and product_category.productId = product.productId and product_category.categoryId=? ";

		try {

			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
			
			statement.setBigDecimal(1, priceCompair);
			statement.setInt(2, categoryId);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String title = resultSet.getString("title");
				String linkImage = resultSet.getString("linkImage");
				BigDecimal price = resultSet.getBigDecimal("price");
				BigDecimal discount = resultSet.getBigDecimal("discount");
				int quantity = resultSet.getInt("quantity");
				String brand = resultSet.getString("brand");
				String createdAt = resultSet.getString("createdAt");
				String content = resultSet.getString("content");

				Product product = new Product();
				product.setId(id);
				product.setTitle(title);
				product.setLinkImage(linkImage);
				product.setPrice(price);
				product.setDiscount(discount);
				product.setQuantity(quantity);
				product.setBrand(brand);
				product.setCreatedAt(createdAt);
				product.setContent(content);
				// System.out.println(user.toString());
				listProduct.add(product);
			}
			dbManager.closeResultSet(resultSet);
			resultSet = statement.executeQuery("SELECT COUNT(*) FROM product,product_category where product.price <=" + priceCompair +" and product_category.productId = product.productId and product_category.categoryId=" + categoryId);
			if (resultSet.next()) {
				this.setTotalRecords(resultSet.getInt(1));
			}
			dbManager.closeStatement(resultSet, statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listProduct;
	}

	public List<Product> getProductCategoryByBrand(int categoryId, String brandCompair) {
		// TODO Auto-generated method stub
		List<Product> listProduct = new ArrayList<Product>();
		String sql = "select * from product,product_category where product.brand like ? and product_category.productId = product.productId and product_category.categoryId=? ";

		try {

			PreparedStatement statement = dbManager.getConnection().prepareStatement(sql);
			
			statement.setString(1, "%" +brandCompair+"%");
			statement.setInt(2, categoryId);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String title = resultSet.getString("title");
				String linkImage = resultSet.getString("linkImage");
				BigDecimal price = resultSet.getBigDecimal("price");
				BigDecimal discount = resultSet.getBigDecimal("discount");
				int quantity = resultSet.getInt("quantity");
				String brand = resultSet.getString("brand");
				String createdAt = resultSet.getString("createdAt");
				String content = resultSet.getString("content");

				Product product = new Product();
				product.setId(id);
				product.setTitle(title);
				product.setLinkImage(linkImage);
				product.setPrice(price);
				product.setDiscount(discount);
				product.setQuantity(quantity);
				product.setBrand(brand);
				product.setCreatedAt(createdAt);
				product.setContent(content);
				// System.out.println(user.toString());
				listProduct.add(product);
			}
			dbManager.closeResultSet(resultSet);
			resultSet = statement.executeQuery("SELECT COUNT(*) FROM product,product_category where product.brand like '%" +  brandCompair +"%' and product_category.productId = product.productId and product_category.categoryId=" + categoryId);
			if (resultSet.next()) {
				this.setTotalRecords(resultSet.getInt(1));
			}
			dbManager.closeStatement(resultSet, statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listProduct;
	}

	
}
