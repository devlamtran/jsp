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
import com.trangialam.dto.GetAllProductRequest;
import com.trangialam.dto.PagingRequest;
import com.trangialam.service.IServiceWeb;
import com.trangialam.service.ServiceWebImpl;

@WebServlet("/home/ProductController")
public class ProductController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	IServiceWeb serviceWeb = new ServiceWebImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int pageNumber = 1;
        HttpSession session = req.getSession();
		if (session.getAttribute("pageNumber") != null) {
			pageNumber = (Integer) session.getAttribute("pageNumber");
		}
		PagingRequest pagingRequest = new PagingRequest();
		pagingRequest.setPageNumber(pageNumber);
		pagingRequest.setRecordsPerPage(6);

		GetAllProductRequest allProductRequest = new GetAllProductRequest();
		allProductRequest.setListProduct(serviceWeb.getProductsPaging(pagingRequest));
		req.setAttribute("listProductRequest", allProductRequest);
		
		GetAllCategoryRequest allCategoryRequest = new GetAllCategoryRequest();
		allCategoryRequest.setListCategories(serviceWeb.getAllCategories());
		req.setAttribute("listCategoriesRequest", allCategoryRequest);

		int totalPages = serviceWeb.getTotalPagesProduct(pagingRequest);
		req.setAttribute("totalPages", totalPages);
		req.setAttribute("currentPage", pagingRequest.getPageNumber());

		RequestDispatcher rd = req.getRequestDispatcher("/views/web/product.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

}
