package com.trangialam.controller.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.trangialam.dto.GetAllProductSearchRequest;
import com.trangialam.dto.PagingRequest;
import com.trangialam.service.IServiceWeb;
import com.trangialam.service.ServiceWebImpl;

@WebServlet("/home/SearchProductController")
public class SearchProductController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IServiceWeb serviceWeb = new ServiceWebImpl();

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int pageNumber = 1;
	    String keyword = "";
	    if (req.getParameter("key")!=null) {
	    	keyword = req.getParameter("key");
		}
	    HttpSession session = req.getSession();
		if (session.getAttribute("pageNumberProductSearch") != null) {
			pageNumber = (Integer) session.getAttribute("pageNumberProductSearch");
		}

		
		PagingRequest pagingRequest = new PagingRequest();
		pagingRequest.setPageNumber(pageNumber);
		pagingRequest.setRecordsPerPage(6);

		GetAllProductSearchRequest allProductRequest = new GetAllProductSearchRequest();
		allProductRequest.setListProduct(serviceWeb.getProductSearchPaging(pagingRequest,keyword));
		req.setAttribute("listProductRequest", allProductRequest);

		int totalPages = serviceWeb.getTotalPagesProductSearch(pagingRequest);
		req.setAttribute("totalPages", totalPages);
		req.setAttribute("currentPage", pagingRequest.getPageNumber());
		req.setAttribute("keyword", keyword);

		RequestDispatcher rd = req.getRequestDispatcher("/views/web/productSearch.jsp");
		rd.forward(req, resp);
	}

	

}
