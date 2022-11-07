package com.trangialam.controller.user;

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
import com.trangialam.service.IServiceWeb;
import com.trangialam.service.ServiceWebImpl;

@WebServlet("/home/ProductCategoryController")
public class ProductCategoryController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IServiceWeb serviceWeb = new ServiceWebImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int pageNumber = 1;
		int categoryId = Integer.parseInt(req.getParameter("categoryId"));

		HttpSession session = req.getSession();
		if (session.getAttribute("pageNumberProductCategory") != null) {
			pageNumber = (Integer) session.getAttribute("pageNumberProductCategory");
		}
		PagingRequest pagingRequest = new PagingRequest();
		pagingRequest.setPageNumber(pageNumber);
		pagingRequest.setRecordsPerPage(6);

		GetAllProductCategoryRequest allProductRequest = new GetAllProductCategoryRequest();
		allProductRequest.setListProduct(serviceWeb.getProductCategoryPaging(pagingRequest, categoryId));
		req.setAttribute("listProductRequest", allProductRequest);
		req.setAttribute("categoryId", categoryId);

		GetAllCategoryRequest allCategoryRequest = new GetAllCategoryRequest();
		allCategoryRequest.setListCategories(serviceWeb.getAllCategories());
		req.setAttribute("listCategoriesRequest", allCategoryRequest);

		int totalPages = serviceWeb.getTotalPagesProductCategory(pagingRequest);
		req.setAttribute("totalPages", totalPages);
		req.setAttribute("currentPage", pagingRequest.getPageNumber());

		RequestDispatcher rd = req.getRequestDispatcher("/views/web/productCategory.jsp");
		rd.forward(req, resp);
	}

}
