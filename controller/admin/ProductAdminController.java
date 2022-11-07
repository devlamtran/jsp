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
import com.trangialam.dto.GetAllProductRequest;
import com.trangialam.dto.PagingRequest;
import com.trangialam.service.IServiceAdmin;
import com.trangialam.service.ServiceAdminImpl;

@WebServlet("/admin/ProductAdminController")
public class ProductAdminController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IServiceAdmin serviceAdmin = new ServiceAdminImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int pageNumber = 1;
		
		HttpSession session = req.getSession();
		if (session.getAttribute("pageNumberAdminProduct") != null) {
			pageNumber = (Integer) session.getAttribute("pageNumberAdminProduct");
		}
		PagingRequest pagingRequest = new PagingRequest();
		pagingRequest.setPageNumber(pageNumber);
		pagingRequest.setRecordsPerPage(6);
		
		GetAllCategoryRequest allCategoryRequest = new GetAllCategoryRequest();
		allCategoryRequest.setListCategories(serviceAdmin.getAllCategory());
		req.setAttribute("listCategoriesRequest", allCategoryRequest);
		
		GetAllProductRequest allProductRequest = new GetAllProductRequest();
		allProductRequest.setListProduct(serviceAdmin.getProductsPaging(pagingRequest));
		req.setAttribute("listProductAdminRequest", allProductRequest);
		
		
        int totalPages = serviceAdmin.getTotalPagesProduct(pagingRequest);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("currentPage", pagingRequest.getPageNumber());
        
		RequestDispatcher rd = req.getRequestDispatcher("/views/admin/productIndex.jsp");
		rd.forward(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
