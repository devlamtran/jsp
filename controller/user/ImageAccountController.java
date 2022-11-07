package com.trangialam.controller.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.trangialam.model.User;
import com.trangialam.service.IServiceWeb;
import com.trangialam.service.ServiceWebImpl;

@WebServlet("/home/ImageAccountController")
public class ImageAccountController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IServiceWeb serviceWeb = new ServiceWebImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setContentType("text/html;charset=UTF-8");
		HttpSession session = req.getSession(false);
		User user = (User) session.getAttribute("user");
		user = serviceWeb.getUserInformation(user.getUserName());
		PrintWriter out = resp.getWriter();
		out.println("/users/" + user.getLinkImage());
	}

}
