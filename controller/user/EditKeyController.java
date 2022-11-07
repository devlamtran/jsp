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
import com.trangialam.dto.ResetPassWordRequest;
import com.trangialam.model.User;
import com.trangialam.service.IServiceWeb;
import com.trangialam.service.ServiceWebImpl;

@WebServlet("/home/EditKeyController")
public class EditKeyController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IServiceWeb serviceWeb = new ServiceWebImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		GetAllCategoryRequest allCategoryRequest = new GetAllCategoryRequest();
		allCategoryRequest.setListCategories(serviceWeb.getAllCategories());
		req.setAttribute("listCategoriesRequest", allCategoryRequest);
		
		RequestDispatcher rd = req.getRequestDispatcher("/views/web/editKey.jsp");
		rd.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
	    User user = (User) session.getAttribute("user");
		
	    String oldPassword = req.getParameter("oldPassword");
		String password = req.getParameter("password");
		String userName = user.getUserName();
		ResetPassWordRequest passwordRequest = new ResetPassWordRequest();
		passwordRequest.setUserName(userName);;
		passwordRequest.setPassword(password);
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUsername(userName);
		loginRequest.setEmail(userName);
		loginRequest.setMobile(userName);
		loginRequest.setPassword(oldPassword);
		if(serviceWeb.authenticate(loginRequest)) {
		if (serviceWeb.resetPassword(passwordRequest)) {
			
			if (session != null) {
				session.removeAttribute("user");
				RequestDispatcher rd = req.getRequestDispatcher("/views/web/home.jsp");
				rd.forward(req, resp);
			}
			
		
		}else {
			req.setAttribute("message", "Invalid user or password");
			doGet(req, resp);
			
		}
		}else {
			req.setAttribute("message", "Invalid user or password");
			doGet(req, resp);
		}
	}

}
