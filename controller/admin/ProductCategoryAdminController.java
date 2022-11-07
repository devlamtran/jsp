package com.trangialam.controller.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.trangialam.dto.GetAllCategoryRequest;
import com.trangialam.dto.GetAllProductCategoryRequest;
import com.trangialam.dto.PagingRequest;
import com.trangialam.service.IServiceAdmin;
import com.trangialam.service.ServiceAdminImpl;

@WebServlet("/admin/ProductCategoryAdminController")
public class ProductCategoryAdminController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IServiceAdmin serviceAdmin = new ServiceAdminImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int pageNumber = 1;
		int categoryId = Integer.parseInt(req.getParameter("categoryId"));

		//HttpSession session = req.getSession();
		if (req.getParameter("pageNumber") != null) {
			pageNumber = Integer.valueOf(req.getParameter("pageNumber"));
		}
		PagingRequest pagingRequest = new PagingRequest();
		pagingRequest.setPageNumber(pageNumber);
		pagingRequest.setRecordsPerPage(2);

		GetAllProductCategoryRequest allProductRequest = new GetAllProductCategoryRequest();
		allProductRequest.setListProduct(serviceAdmin.getProductCategoryPaging(pagingRequest, categoryId));
		req.setAttribute("listProductAdminRequest", allProductRequest);
		req.setAttribute("categoryId", categoryId);

		GetAllCategoryRequest allCategoryRequest = new GetAllCategoryRequest();
		allCategoryRequest.setListCategories(serviceAdmin.getAllCategory());
		req.setAttribute("listCategoriesRequest", allCategoryRequest);

		int totalPages = serviceAdmin.getTotalPagesProductCategory(pagingRequest);
		req.setAttribute("totalPages", totalPages);
		req.setAttribute("currentPage", pagingRequest.getPageNumber());

		RequestDispatcher rd = req.getRequestDispatcher("/views/admin/productCategoryIndex.jsp");
		rd.forward(req, resp);
	}

}
