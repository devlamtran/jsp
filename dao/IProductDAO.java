package com.trangialam.dao;


import java.math.BigDecimal;
import java.util.List;


import com.trangialam.model.Product;

public interface IProductDAO {
	public boolean saveProduct(Product product);
	public List<Product> getAllProducts() ;
	public List<Product> getProductsLimit(int offset, int records);
	public int getTotalRecords();
	public boolean updateProduct(Product product);
	public Product getProductById(int productId);
	public boolean deleteProductById(int productId);
	public List<Product> getProductCategoryLimit(int offset, int records, int categoryId);
	public List<Product> getProductSearchLimit(int offset, int records, String keyword);
	public List<Product> getProductsByPrice(BigDecimal price);
	public List<Product> getProductsByBrand(String brand);
	public List<Product> getProductsByBrandPrice(BigDecimal price, String brand);
	public List<Product> getProductCategoryByBrandPrice(int categoryId, BigDecimal priceCompair, String brandCompair);
	public List<Product> getProductCategoryByPrice(int categoryId, BigDecimal priceCompair);
	public List<Product> getProductCategoryByBrand(int categoryId, String brandCompair);
	
	
	

}
