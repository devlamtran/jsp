package com.trangialam.controller.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trangialam.dto.GetAllCategoryRequest;
import com.trangialam.dto.RegisterRequest;
import com.trangialam.service.IServiceWeb;
import com.trangialam.service.ServiceWebImpl;

@WebServlet("/home/RegisterController")
public class RegisterController extends HttpServlet {

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
		RequestDispatcher rd = req.getRequestDispatcher("/views/web/register.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String firstName = req.getParameter("firstName");
		String middleName = req.getParameter("middleName");
		String lastName = req.getParameter("lastName");
		String userName = req.getParameter("userName");
		String email = req.getParameter("email");
		String mobile = req.getParameter("mobile");
		String password = req.getParameter("password");
		RegisterRequest registerRequest = new RegisterRequest();
		registerRequest.setFirstName(firstName);
		registerRequest.setMiddleName(middleName);
		registerRequest.setLastName(lastName);
		registerRequest.setUserName(userName);
		registerRequest.setEmail(email);
		registerRequest.setMobile(mobile);
		registerRequest.setPassword(password);
		if (serviceWeb.register(registerRequest)) {
			RequestDispatcher rd = req.getRequestDispatcher("/views/web/home.jsp");
			rd.forward(req, resp);
		
		}else {
			doGet(req, resp);
			
		}
		
	}
}
