package com.trangialam.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trangialam.dto.AssignRoleRequest;
import com.trangialam.dto.GetAllRoleRequest;
import com.trangialam.dto.GetAllUserRoleRequest;
import com.trangialam.dto.UserDetailRequest;
import com.trangialam.service.IServiceAdmin;
import com.trangialam.service.ServiceAdminImpl;

@WebServlet("/admin/AssignRoleController")
public class AssignRoleController extends HttpServlet {

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
		
		GetAllRoleRequest allRoleRequest = new GetAllRoleRequest();
		allRoleRequest.setListRoles(serviceAdmin.getAllRole());
		req.setAttribute("listRolesAdminRequest", allRoleRequest);
		
		GetAllUserRoleRequest allUserRoleRequest = new GetAllUserRoleRequest();
		allUserRoleRequest.setListRoleIDs(serviceAdmin.getAllUserRoleIdByUserId(userId));
		 req.setAttribute("listUserRole", allUserRoleRequest);
		
		RequestDispatcher rd = req.getRequestDispatcher("/views/admin/assignRole.jsp");
		rd.forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		AssignRoleRequest assignRoleRequest = new AssignRoleRequest();
		int userId = Integer.valueOf(req.getParameter("userId"));
		if (req.getParameterValues("roleId")!= null) {
			String[] roleIds = req.getParameterValues("roleId");
			List<String> listRoleId =  Arrays.asList(roleIds);
			List<Integer> listRolesId = new ArrayList<Integer>();
			for (Integer roleId : convertIntList(listRoleId)) {
				listRolesId.add(roleId);
			}
			assignRoleRequest.setListRolesIdAssign(listRolesId );
			assignRoleRequest.setUserId(userId);
			if(serviceAdmin.assignRole(assignRoleRequest)) {
				resp.sendRedirect("CustomerAdminController");
			}else {
			   doGet(req,resp);}
			
			
		}else {
			if (serviceAdmin.getAllUserRoleIdByUserId(userId).size()>0) {
				if(serviceAdmin.deleteUserRole(userId)) {;
				 resp.sendRedirect("CustomerAdminController");
				}else {
					doGet(req,resp);
				}
			}else {
				resp.sendRedirect("CustomerAdminController");}
		}
		
	}
	private List<Integer> convertIntList(List<String> list){
		List<Integer> convertList = new ArrayList<Integer>();
		for (String s : list) {
			convertList.add(Integer.valueOf(s));
		}
		return convertList;
	}

}
