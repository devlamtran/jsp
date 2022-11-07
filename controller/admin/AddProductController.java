package com.trangialam.controller.admin;



import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;



import com.trangialam.dto.AddProductRequest;
import com.trangialam.model.User;
import com.trangialam.service.IServiceAdmin;
import com.trangialam.service.ServiceAdminImpl;

@WebServlet("/admin/AddProductController")
@MultipartConfig()
public class AddProductController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IServiceAdmin serviceAdmin = new ServiceAdminImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher rd = req.getRequestDispatcher("/views/admin/createProduct.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		String title = req.getParameter("title");
		String brand = req.getParameter("brand");
		BigDecimal price = BigDecimal.valueOf(Double.valueOf(req.getParameter("price")));
		int quantity = Integer.valueOf(req.getParameter("quantity"));
		BigDecimal discount = BigDecimal.valueOf(Double.valueOf(req.getParameter("discount")));
		String content = req.getParameter("content");
		
		Part filePart = req.getPart("photo");
		String fileName = filePart.getSubmittedFileName();
		String realPath = req.getServletContext().getRealPath("/products");
		
		for (Part part : req.getParts()) {
			part.write(realPath + "/" + fileName);
		}
		

		AddProductRequest addProductRequest = new AddProductRequest();
		addProductRequest.setUserId(user.getId());
		addProductRequest.setTitle(title);
		addProductRequest.setBrand(brand);
		addProductRequest.setPrice(price);
		addProductRequest.setQuantity(quantity);
		addProductRequest.setDiscount(discount);
		addProductRequest.setContent(content);
		addProductRequest.setLinkImage(fileName);

		
		if (serviceAdmin.addProduct(addProductRequest)) {
//			RequestDispatcher rd = req.getRequestDispatcher("/views/admin/productAdmin.jsp");
//			rd.forward(req, resp);
			resp.sendRedirect("ProductAdminController");

			
		} else {
			doGet(req, resp);

		}

	}

}
