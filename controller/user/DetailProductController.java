package com.trangialam.controller.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trangialam.dto.GetAllCategoryRequest;
import com.trangialam.dto.ProductDetailRequest;
import com.trangialam.service.IServiceWeb;
import com.trangialam.service.ServiceWebImpl;


@WebServlet("/home/DetailProductController")
public class DetailProductController extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	IServiceWeb serviceWeb = new ServiceWebImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int productId = Integer.valueOf(req.getParameter("productId"));
		ProductDetailRequest  productDetailRequest = new ProductDetailRequest();
		productDetailRequest.setProduct(serviceWeb.getProductById(productId));
		req.setAttribute("productDetailRequest", productDetailRequest);
		
		GetAllCategoryRequest allCategoryRequest = new GetAllCategoryRequest();
		allCategoryRequest.setListCategories(serviceWeb.getAllCategories());
		req.setAttribute("listCategoriesRequest", allCategoryRequest);
		
		RequestDispatcher rd = req.getRequestDispatcher("/views/web/productDetail.jsp");
		rd.forward(req, resp);
	}

}
