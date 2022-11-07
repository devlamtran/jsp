package com.trangialam.controller.admin;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.trangialam.dto.EditProductRequest;

import com.trangialam.dto.ProductDetailRequest;
import com.trangialam.model.User;
import com.trangialam.service.IServiceAdmin;
import com.trangialam.service.ServiceAdminImpl;

@WebServlet("/admin/EditProductController")
public class EditProductController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IServiceAdmin serviceAdmin = new ServiceAdminImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int productId = Integer.valueOf(req.getParameter("productId"));
		ProductDetailRequest  productDetailRequest = new ProductDetailRequest();
		productDetailRequest.setProduct(serviceAdmin.getProductById(productId));
		req.setAttribute("productDetailRequest", productDetailRequest);
		
		RequestDispatcher rd = req.getRequestDispatcher("/views/admin/editProduct.jsp");
		rd.forward(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int productId = Integer.valueOf(req.getParameter("productId"));
		HttpSession session = req.getSession(false);
		User user = (User) session.getAttribute("user");
		String title = req.getParameter("title");
		String brand = req.getParameter("brand");
		BigDecimal price = BigDecimal.valueOf(Double.valueOf(req.getParameter("price")));
		int quantity = Integer.valueOf(req.getParameter("quantity"));
		BigDecimal discount = BigDecimal.valueOf(Double.valueOf(req.getParameter("discount")));
		String content = req.getParameter("content");
//		Part filePart = req.getPart("photo");
//		String fileName = filePart.getSubmittedFileName();
//		String realPath = req.getServletContext().getRealPath("/products");
		
//		for (Part part : req.getParts()) {
//			part.write(realPath + "/" + fileName);
//		}
		//System.out.println(realPath + "/" + fileName);
		EditProductRequest editProductRequest = new EditProductRequest();
		editProductRequest.setId(productId);
		editProductRequest.setUserId(user.getId());
		editProductRequest.setTitle(title);
		editProductRequest.setBrand(brand);
	    editProductRequest.setPrice(price);
		editProductRequest.setQuantity(quantity);
		editProductRequest.setDiscount(discount);
		editProductRequest.setContent(content);
	//	editProductRequest.setLinkImage(fileName);
		
		if (serviceAdmin.editProduct(editProductRequest)) {
			resp.sendRedirect("ProductAdminController");
			

		} else {
			doGet(req, resp);

		}

	}

}
