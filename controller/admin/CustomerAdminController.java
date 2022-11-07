package com.trangialam.controller.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.trangialam.dto.GetAllUserRequest;
import com.trangialam.dto.PagingRequest;
import com.trangialam.service.IServiceAdmin;
import com.trangialam.service.ServiceAdminImpl;

@WebServlet("/admin/CustomerAdminController")
public class CustomerAdminController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IServiceAdmin serviceAdmin = new ServiceAdminImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int pageNumber = 1;
		HttpSession session = req.getSession();
		if (session.getAttribute("pageNumberAdminCustomer") != null) {
			pageNumber = (Integer) session.getAttribute("pageNumberAdminCustomer");
		}
		PagingRequest pagingRequest = new PagingRequest();
		pagingRequest.setPageNumber(pageNumber);
		pagingRequest.setRecordsPerPage(6);
		
		GetAllUserRequest allUserRequest = new GetAllUserRequest();
		allUserRequest.setListUser(serviceAdmin.getUsersPaging(pagingRequest));
		req.setAttribute("listUserAdminRequest", allUserRequest);
		
        int totalPages = serviceAdmin.getTotalPagesUser(pagingRequest);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("currentPage", pagingRequest.getPageNumber());
        
		RequestDispatcher rd = req.getRequestDispatcher("/views/admin/customerIndex.jsp");
		rd.forward(req, resp);
		
	}

	

}
