package com.trangialam.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trangialam.dto.AssignCategoryRequest;
import com.trangialam.dto.CategoryDetailRequest;
import com.trangialam.dto.GetAllCategoryParentRequest;
import com.trangialam.dto.GetAllCategoryRequest;
import com.trangialam.service.IServiceAdmin;
import com.trangialam.service.ServiceAdminImpl;

@WebServlet("/admin/AssignCategoryController")
public class AssignCategoryController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IServiceAdmin serviceAdmin = new ServiceAdminImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int categoryId = Integer.valueOf(req.getParameter("categoryId"));
		CategoryDetailRequest categoryDetailRequest = new CategoryDetailRequest();
		categoryDetailRequest.setCategory(serviceAdmin.getCategoryById(categoryId));
		
		req.setAttribute("categoryDetailRequest", categoryDetailRequest);

		GetAllCategoryRequest allCategoryRequest = new GetAllCategoryRequest();
		allCategoryRequest.setListCategories(serviceAdmin.getAllCategory());
		req.setAttribute("listCategoriesAdminRequest", allCategoryRequest);

		GetAllCategoryParentRequest allCategoryParentRequest = new GetAllCategoryParentRequest();
		allCategoryParentRequest.setListCategoryIDs(serviceAdmin.getAllCategoryParentIdByCategoryId(categoryId));
		req.setAttribute("listCategoriesParent", allCategoryParentRequest);

		RequestDispatcher rd = req.getRequestDispatcher("/views/admin/assignCategory.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		AssignCategoryRequest assignCategoryRequest = new AssignCategoryRequest();
		int categoryId =  Integer.valueOf(req.getParameter("categoryId"));
		int categoryIdAssign = 0;
		if (req.getParameter("categoryIds")!= null) {
			 categoryIdAssign = Integer.valueOf(req.getParameter("categoryIds"));
			
			List<Integer> listCategoriesId = new ArrayList<Integer>();
			
			listCategoriesId.add(categoryIdAssign);
			
			assignCategoryRequest.setListCategoriesAssign(listCategoriesId);
			assignCategoryRequest.setCategoryId(categoryId);
			if(serviceAdmin.assignCategory(assignCategoryRequest)) {
				resp.sendRedirect("CategoryAdminController");
			}else {
			   doGet(req,resp);}
			
			
		}else {
			 doGet(req,resp);
		}
		
	}
	

}
