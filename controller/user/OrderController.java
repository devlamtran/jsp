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
import com.trangialam.dto.GetAllOrderCustomerRequest;
import com.trangialam.dto.PagingRequest;
import com.trangialam.model.User;
import com.trangialam.service.IServiceWeb;
import com.trangialam.service.ServiceWebImpl;

@WebServlet("/home/OrderController")
public class OrderController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IServiceWeb serviceWeb = new ServiceWebImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		
		if (session.getAttribute("user")==null) {
			RequestDispatcher rd = req.getRequestDispatcher("/views/web/login.jsp");
			rd.forward(req, resp);
		}
		
		GetAllCategoryRequest allCategoryRequest = new GetAllCategoryRequest();
		allCategoryRequest.setListCategories(serviceWeb.getAllCategories());
		req.setAttribute("listCategoriesRequest", allCategoryRequest);
		
		int pageNumber = 1;
		User user = (User) session.getAttribute("user");
		int userId = user.getId();

		if (req.getParameter("pageNumber") != null) {
			pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
		}
		PagingRequest pagingRequest = new PagingRequest();
		pagingRequest.setPageNumber(pageNumber);
		pagingRequest.setRecordsPerPage(2);
		
		GetAllOrderCustomerRequest allOrderCustomerRequest = new GetAllOrderCustomerRequest();
		allOrderCustomerRequest.setListOrders(serviceWeb.getOrdersPaging(pagingRequest, userId));
		req.setAttribute("listOrdersRequest", allOrderCustomerRequest);
		
		int totalPages = serviceWeb.getTotalPagesOrderByUserId(pagingRequest);
		req.setAttribute("totalPages", totalPages);
		req.setAttribute("currentPage", pagingRequest.getPageNumber());
		
		RequestDispatcher rd = req.getRequestDispatcher("/views/web/order.jsp");
		rd.forward(req, resp);
		
	}
}
