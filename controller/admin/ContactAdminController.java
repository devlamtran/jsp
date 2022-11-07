package com.trangialam.controller.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trangialam.dto.GetAllContactRequest;
import com.trangialam.dto.PagingRequest;
import com.trangialam.service.IServiceAdmin;
import com.trangialam.service.ServiceAdminImpl;

@WebServlet("/admin/ContactAdminController")
public class ContactAdminController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IServiceAdmin serviceAdmin = new ServiceAdminImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int pageNumber = 1;

		if (req.getParameter("pageNumber") != null) {
			pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
		}
		PagingRequest pagingRequest = new PagingRequest();
		pagingRequest.setPageNumber(pageNumber);
		pagingRequest.setRecordsPerPage(6);

		GetAllContactRequest allContactRequest = new GetAllContactRequest();
		allContactRequest.setListContacts(serviceAdmin.getContactsPaging(pagingRequest));
		req.setAttribute("listContactsAdminRequest", allContactRequest);

		int totalPages = serviceAdmin.getTotalPagesContact(pagingRequest);
		req.setAttribute("totalPages", totalPages);
		req.setAttribute("currentPage", pagingRequest.getPageNumber());

		RequestDispatcher rd = req.getRequestDispatcher("/views/admin/contactIndex.jsp");
		rd.forward(req, resp);
	}

}
