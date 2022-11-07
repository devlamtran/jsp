package com.trangialam.controller.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trangialam.dto.GetAllCategoryRequest;
import com.trangialam.dto.OrderDetailRequest;
import com.trangialam.service.IServiceWeb;
import com.trangialam.service.ServiceWebImpl;

@WebServlet("/home/DetailOrderController")
public class DetailOrderController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IServiceWeb serviceWeb = new ServiceWebImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int orderId = Integer.parseInt(req.getParameter("orderId"));
		
		OrderDetailRequest orderDetailRequest = new OrderDetailRequest();
		
		orderDetailRequest.setOrder(serviceWeb.getOrder(orderId));
		req.setAttribute("detailOrderRequest", orderDetailRequest);
		
		GetAllCategoryRequest allCategoryRequest = new GetAllCategoryRequest();
		allCategoryRequest.setListCategories(serviceWeb.getAllCategories());
		req.setAttribute("listCategoriesRequest", allCategoryRequest);

		
		RequestDispatcher rd = req.getRequestDispatcher("/views/web/detailOrder.jsp");
		rd.forward(req, resp);

		
	}

}
