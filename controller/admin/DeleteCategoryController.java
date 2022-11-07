package com.trangialam.controller.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trangialam.dto.CategoryDetailRequest;
import com.trangialam.dto.DeleteCategoryRequest;
import com.trangialam.service.IServiceAdmin;
import com.trangialam.service.ServiceAdminImpl;

@WebServlet("/admin/DeleteCategoryController")
public class DeleteCategoryController extends HttpServlet {

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
		categoryDetailRequest.setCategory(serviceAdmin.getCategoryById(categoryId));;;
		req.setAttribute("categoryDetailRequest", categoryDetailRequest);
		
		RequestDispatcher rd = req.getRequestDispatcher("/views/admin/deleteCategory.jsp");
		rd.forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int categoryId = Integer.valueOf(req.getParameter("categoryId"));
		DeleteCategoryRequest deleteCategoryRequest = new DeleteCategoryRequest();
		deleteCategoryRequest.setCategoryId(categoryId);;
		if (serviceAdmin.deleteCategory(deleteCategoryRequest)) {
			resp.sendRedirect("CategoryAdminController");
		} else {
            doGet(req, resp);
		}
	}

}
