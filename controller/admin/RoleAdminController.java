package com.trangialam.controller.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trangialam.dto.GetAllCategoryRequest;
import com.trangialam.dto.GetAllRoleRequest;
import com.trangialam.dto.PagingRequest;
import com.trangialam.service.IServiceAdmin;
import com.trangialam.service.ServiceAdminImpl;

@WebServlet("/admin/RoleAdminController")
public class RoleAdminController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IServiceAdmin serviceAdmin = new ServiceAdminImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		

		GetAllRoleRequest allRoleRequest = new GetAllRoleRequest();
		allRoleRequest.setListRoles(serviceAdmin.getAllRole());
		req.setAttribute("listRolesAdminRequest", allRoleRequest);

		
		RequestDispatcher rd = req.getRequestDispatcher("/views/admin/roleIndex.jsp");
		rd.forward(req, resp);
	}

}
