package com.trangialam.controller.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trangialam.dto.GetAllCategoryRequest;
import com.trangialam.dto.PagingRequest;
import com.trangialam.service.IServiceAdmin;
import com.trangialam.service.ServiceAdminImpl;

@WebServlet("/admin/CategoryAdminController")
public class CategoryAdminController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private IServiceAdmin serviceAdmin = new ServiceAdminImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int pageNumber = 1;

		if (req.getParameter("pageNumber") != null) {
			pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
		}
		PagingRequest pagingRequest = new PagingRequest();
		pagingRequest.setPageNumber(pageNumber);
		pagingRequest.setRecordsPerPage(6);

		GetAllCategoryRequest allCategoryRequest = new GetAllCategoryRequest();
		allCategoryRequest.setListCategories(serviceAdmin.getCategoriesPaging(pagingRequest));
		req.setAttribute("listCategoriesAdminRequest", allCategoryRequest);

		int totalPages = serviceAdmin.getTotalPagesCategory(pagingRequest);
		req.setAttribute("totalPages", totalPages);
		req.setAttribute("currentPage", pagingRequest.getPageNumber());

		RequestDispatcher rd = req.getRequestDispatcher("/views/admin/categoryIndex.jsp");
		rd.forward(req, resp);
	}

}
