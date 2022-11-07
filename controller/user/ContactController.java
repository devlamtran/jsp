package com.trangialam.controller.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trangialam.dto.ContactRequest;
import com.trangialam.dto.GetAllCategoryRequest;
import com.trangialam.service.IServiceWeb;
import com.trangialam.service.ServiceWebImpl;

@WebServlet("/home/ContactController")
public class ContactController extends HttpServlet {

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

		RequestDispatcher rd = req.getRequestDispatcher("/views/web/contact.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String subject = req.getParameter("subject");
		String message = req.getParameter("message");

		ContactRequest contactRequest = new ContactRequest();
		contactRequest.setName(name);
		contactRequest.setEmail(email);
		contactRequest.setSubject(subject);
		contactRequest.setMessage(message);

		if (serviceWeb.saveContactUser(contactRequest)) {

			RequestDispatcher rd = req.getRequestDispatcher("/views/web/home.jsp");
			rd.forward(req, resp);

		} else {

			doGet(req, resp);

		}

	}
}
