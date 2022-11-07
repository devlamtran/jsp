package com.trangialam.controller.admin;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.trangialam.dto.AddCategoryRequest;
import com.trangialam.service.IServiceAdmin;
import com.trangialam.service.ServiceAdminImpl;

@WebServlet("/admin/AddCategoryController")
public class AddCategoryController extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IServiceAdmin serviceAdmin = new ServiceAdminImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher rd = req.getRequestDispatcher("/views/admin/createCategory.jsp");
		rd.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		
		

		AddCategoryRequest addCategoryRequest = new AddCategoryRequest();
		
		addCategoryRequest.setTitle(title);
		
		addCategoryRequest.setContent(content);
		

		
		if (serviceAdmin.addCategory(addCategoryRequest)) {

			resp.sendRedirect("CategoryAdminController");

			
		} else {
			doGet(req, resp);

		}

	}

}
