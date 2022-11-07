package com.trangialam.controller.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trangialam.dto.DeleteProductRequest;
import com.trangialam.dto.ProductDetailRequest;
import com.trangialam.service.IServiceAdmin;
import com.trangialam.service.ServiceAdminImpl;

@WebServlet("/admin/DeleteProductController")
public class DeleteProductController extends HttpServlet {

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
		
		RequestDispatcher rd = req.getRequestDispatcher("/views/admin/deleteProduct.jsp");
		rd.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int productId = Integer.valueOf(req.getParameter("productId"));
		DeleteProductRequest deleteProductRequest = new DeleteProductRequest();
		deleteProductRequest.setProductId(productId);
		if (serviceAdmin.deleteProduct(deleteProductRequest)) {
			resp.sendRedirect("ProductAdminController");
		} else {
            doGet(req, resp);
		}
		
	}

}
