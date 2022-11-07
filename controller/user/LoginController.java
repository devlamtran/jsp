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
import com.trangialam.dto.LoginRequest;
import com.trangialam.service.IServiceWeb;
import com.trangialam.service.ServiceWebImpl;

@WebServlet("/home/LoginController")
public class LoginController extends HttpServlet{
	
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
		
		RequestDispatcher rd = req.getRequestDispatcher("/views/web/login.jsp");
		rd.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String userName = req.getParameter("userName");
		
		String password = req.getParameter("password");
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUsername(userName);
		loginRequest.setEmail(userName);
		loginRequest.setMobile(userName);
		loginRequest.setPassword(password);
		
		if (serviceWeb.authenticate(loginRequest)) {
			HttpSession session = req.getSession();
			session.setAttribute("user", serviceWeb.getUserInformation(loginRequest.getUsername()));
			RequestDispatcher rd = req.getRequestDispatcher("/views/web/home.jsp");
			rd.forward(req, resp);
		
		}else {
			req.setAttribute("message", "Invalid user or password");
			doGet(req, resp);
			
		}
	}
	

}
