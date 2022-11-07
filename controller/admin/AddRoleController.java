package com.trangialam.controller.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trangialam.dto.AddRoleRequest;
import com.trangialam.service.IServiceAdmin;
import com.trangialam.service.ServiceAdminImpl;

@WebServlet("/admin/AddRoleController")
public class AddRoleController extends HttpServlet{

	
	private static final long serialVersionUID = 1L;
	private IServiceAdmin serviceAdmin = new ServiceAdminImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher rd = req.getRequestDispatcher("/views/admin/createRole.jsp");
		rd.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = req.getParameter("name");
		String description = req.getParameter("description");
		
		

		AddRoleRequest addRoleRequest = new AddRoleRequest();
		
		addRoleRequest.setName(name);
		
		addRoleRequest.setDescription(description);
		

		
		if (serviceAdmin.addRole(addRoleRequest)) {

			resp.sendRedirect("RoleAdminController");

			
		} else {
			doGet(req, resp);

		}
	}

}
