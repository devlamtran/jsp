package com.trangialam.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trangialam.dto.AssignProductRequest;
import com.trangialam.dto.GetAllCategoryRequest;
import com.trangialam.dto.GetAllProductCategoryIdRequest;
import com.trangialam.dto.ProductDetailRequest;
import com.trangialam.service.IServiceAdmin;
import com.trangialam.service.ServiceAdminImpl;

@WebServlet("/admin/AssignProductController")
public class AssignProductController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IServiceAdmin serviceAdmin = new ServiceAdminImpl();

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int productId = Integer.valueOf(req.getParameter("productId"));
		ProductDetailRequest  productDetailRequest = new ProductDetailRequest();
		productDetailRequest.setProduct(serviceAdmin.getProductById(productId));
		req.setAttribute("productDetailRequest", productDetailRequest);
		
		GetAllCategoryRequest allCategoryRequest = new GetAllCategoryRequest();
		allCategoryRequest.setListCategories(serviceAdmin.getAllCategory());
		req.setAttribute("listCategoriesAdminRequest", allCategoryRequest);
		
	    GetAllProductCategoryIdRequest allProductCategoryRequest = new GetAllProductCategoryIdRequest();
	    allProductCategoryRequest.setListCategoryIDs(serviceAdmin.getAllProductCategoryIdByProductId(productId));
	    req.setAttribute("listCategoriesProduct", allProductCategoryRequest);
	    
		RequestDispatcher rd = req.getRequestDispatcher("/views/admin/assignProduct.jsp");
		rd.forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		AssignProductRequest assignProductRequest = new AssignProductRequest();
		int productId = Integer.valueOf(req.getParameter("productId"));
		if (req.getParameterValues("categoryId")!= null) {
			String[] categoryIds = req.getParameterValues("categoryId");
			List<String> listCategoryId =  Arrays.asList(categoryIds);
			List<Integer> listCategoriesId = new ArrayList<Integer>();
			for (Integer categoryId : convertIntList(listCategoryId)) {
				listCategoriesId.add(categoryId);
			}
			assignProductRequest.setListCategoriesAssign(listCategoriesId);
			assignProductRequest.setProductId(productId);
			if(serviceAdmin.assignProduct(assignProductRequest)) {
				resp.sendRedirect("ProductAdminController");
			}else {
			   doGet(req,resp);}
			
			
		}else {
			if (serviceAdmin.getAllProductCategoryIdByProductId(productId).size()>0) {
				if(serviceAdmin.deleteProductCategory(productId)) {;
				 resp.sendRedirect("ProductAdminController");
				}else {
					doGet(req,resp);
				}
			}else {
				resp.sendRedirect("ProductAdminController");
			}
		}
		
		
	}
	
	private List<Integer> convertIntList(List<String> list){
		List<Integer> convertList = new ArrayList<Integer>();
		for (String s : list) {
			convertList.add(Integer.valueOf(s));
		}
		return convertList;
	}

}
