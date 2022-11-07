package com.trangialam.controller.user;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.trangialam.dto.AddProductRequest;
import com.trangialam.dto.GetAllCategoryRequest;
import com.trangialam.dto.UpdateUserRequest;
import com.trangialam.model.User;
import com.trangialam.service.IServiceWeb;
import com.trangialam.service.ServiceWebImpl;

@WebServlet("/home/AccountController")
@MultipartConfig()
public class AccountController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IServiceWeb serviceWeb = new ServiceWebImpl();
	private static final String UPLOAD_DIR = "uploads";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		GetAllCategoryRequest allCategoryRequest = new GetAllCategoryRequest();
		allCategoryRequest.setListCategories(serviceWeb.getAllCategories());
		req.setAttribute("listCategoriesRequest", allCategoryRequest);
		
		HttpSession session = req.getSession(false);
		User user = (User) session.getAttribute("user");
		user = serviceWeb.getUserInformation(user.getUserName());
		session.setAttribute("user", user);
		RequestDispatcher rd = req.getRequestDispatcher("/views/web/account.jsp");
		rd.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession(false);
		User user = (User) session.getAttribute("user");
		int id = user.getId();
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String middleName = req.getParameter("middleName");
		String mobile = req.getParameter("phoneNumber");
		String email = req.getParameter("email");
		String userName = req.getParameter("userName");

		Part filePart = req.getPart("photoProfile");
		String fileName = filePart.getSubmittedFileName();
		String realPath = req.getServletContext().getRealPath("/users");
		       
		for (Part part : req.getParts()) {
			
			part.write(realPath + "/" + fileName);
		}
		

		UpdateUserRequest updateUserRequest = new UpdateUserRequest();
		updateUserRequest.setId(id);
		updateUserRequest.setFirstName(firstName);;
		updateUserRequest.setLastName(lastName);
		updateUserRequest.setMiddleName(middleName);
		updateUserRequest.setMobile(mobile);
		updateUserRequest.setUserName(userName);
		updateUserRequest.setLinkImage(fileName);
		updateUserRequest.setEmail(email);
		
		if (serviceWeb.updateUser(updateUserRequest)) {
			RequestDispatcher rd = req.getRequestDispatcher("/views/web/home.jsp");
			rd.forward(req, resp);
		} else {
			doGet(req, resp);

		}
	}
	
	
}
