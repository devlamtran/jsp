package com.trangialam.controller.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trangialam.dto.DeleteRoleRequest;
import com.trangialam.dto.RoleDetailRequest;
import com.trangialam.service.IServiceAdmin;
import com.trangialam.service.ServiceAdminImpl;

@WebServlet("/admin/DeleteRoleController")
public class DeleteRoleController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IServiceAdmin serviceAdmin = new ServiceAdminImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int roleId = Integer.valueOf(req.getParameter("roleId"));
		RoleDetailRequest  roleDetailRequest = new RoleDetailRequest();
		roleDetailRequest.setRole(serviceAdmin.getRoleById(roleId));
		req.setAttribute("roleDetailRequest", roleDetailRequest);
		
		RequestDispatcher rd = req.getRequestDispatcher("/views/admin/deleteRole.jsp");
		rd.forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int roleId = Integer.valueOf(req.getParameter("roleId"));
		DeleteRoleRequest deleteRoleRequest = new DeleteRoleRequest();
		deleteRoleRequest.setRoleId(roleId);;
		if (serviceAdmin.deleteRole(deleteRoleRequest)) {
			resp.sendRedirect("RoleAdminController");
		} else {
            doGet(req, resp);
		}
	}

}
