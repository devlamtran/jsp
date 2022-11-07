package com.trangialam.controller.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.trangialam.dto.CategoryDetailRequest;
import com.trangialam.dto.EditCategoryRequest;

import com.trangialam.service.IServiceAdmin;
import com.trangialam.service.ServiceAdminImpl;

@WebServlet("/admin/EditCategoryController")
public class EditCategoryController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IServiceAdmin serviceAdmin = new ServiceAdminImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int categoryId = Integer.valueOf(req.getParameter("categoryId"));
		CategoryDetailRequest  categoryDetailRequest = new CategoryDetailRequest();
		categoryDetailRequest.setCategory(serviceAdmin.getCategoryById(categoryId));;
		req.setAttribute("categoryDetailRequest", categoryDetailRequest);
		
		RequestDispatcher rd = req.getRequestDispatcher("/views/admin/editCategory.jsp");
		rd.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int categoryId = Integer.valueOf(req.getParameter("categoryId"));
		
		String title = req.getParameter("title");
		String content = req.getParameter("content");
//		
		EditCategoryRequest editProductRequest = new EditCategoryRequest();
		editProductRequest.setId(categoryId);
		editProductRequest.setTitle(title);
		
		editProductRequest.setContent(content);
	//	editProductRequest.setLinkImage(fileName);
		
		if (serviceAdmin.editCategory(editProductRequest)) {
			resp.sendRedirect("CategoryAdminController");
			

		} else {
			doGet(req, resp);

		}

	}

}
