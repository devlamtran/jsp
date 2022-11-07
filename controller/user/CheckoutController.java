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
import com.trangialam.dto.NewOrderRequest;
import com.trangialam.model.Order;
import com.trangialam.model.User;
import com.trangialam.service.IServiceWeb;
import com.trangialam.service.ServiceWebImpl;

@WebServlet("/home/CheckoutController")
public class CheckoutController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IServiceWeb serviceWeb = new ServiceWebImpl();

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		GetAllCategoryRequest allCategoryRequest = new GetAllCategoryRequest();
		allCategoryRequest.setListCategories(serviceWeb.getAllCategories());
		req.setAttribute("listCategoriesRequest", allCategoryRequest);
		
		RequestDispatcher rd = req.getRequestDispatcher("/views/web/checkout.jsp");
		rd.forward(req, resp);
	
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		NewOrderRequest newOrderRequest = new NewOrderRequest();
		HttpSession session = req.getSession();
		if (session.getAttribute("order") != null) {
			newOrderRequest.setOrder((Order) session.getAttribute("order"));
			
			
		}
		if (session.getAttribute("user") != null) {
			newOrderRequest.setCustomer((User) session.getAttribute("user"));
		}
		
		
		newOrderRequest.setFirstName(req.getParameter("firstName"));
		newOrderRequest.setLastName(req.getParameter("lastName"));
		newOrderRequest.setMiddleName(req.getParameter("middleName"));
		newOrderRequest.setMobile(req.getParameter("mobile"));
		newOrderRequest.setEmail(req.getParameter("email"));
		newOrderRequest.setCity(req.getParameter("city"));
		newOrderRequest.setProvince(req.getParameter("province"));
		newOrderRequest.setCountry(req.getParameter("country"));
		newOrderRequest.setContent(req.getParameter("content"));
		newOrderRequest.setSessionId(session.getId());
		newOrderRequest.setToken("token");
		serviceWeb.addOrderRequest(newOrderRequest);
		
		
	}

}
