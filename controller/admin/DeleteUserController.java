package com.trangialam.controller.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trangialam.dto.DeleteUserRequest;
import com.trangialam.dto.GetAllUserRoleRequest;
import com.trangialam.dto.UserDetailRequest;
import com.trangialam.service.IServiceAdmin;
import com.trangialam.service.ServiceAdminImpl;
@WebServlet("/admin/DeleteUserController")
public class DeleteUserController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IServiceAdmin serviceAdmin = new ServiceAdminImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int userId = Integer.valueOf(req.getParameter("userId"));
		UserDetailRequest  userDetailRequest = new UserDetailRequest();
		userDetailRequest.setUser(serviceAdmin.getUserById(userId));
		req.setAttribute("userDetailRequest", userDetailRequest);
		
		
		GetAllUserRoleRequest allUserRoleRequest = new GetAllUserRoleRequest();
		allUserRoleRequest.setListRoleIDs(serviceAdmin.getAllUserRoleIdByUserId(userId));
		 req.setAttribute("listUserRole", allUserRoleRequest);
		
		RequestDispatcher rd = req.getRequestDispatcher("/views/admin/deleteUser.jsp");
		rd.forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int userId = Integer.valueOf(req.getParameter("userId"));
		DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
		deleteUserRequest .setUserId(userId);
		if (serviceAdmin.deleteUser(deleteUserRequest)) {
			resp.sendRedirect("CustomerAdminController");
		} else {
            doGet(req, resp);
		}
	}

}
