package com.trangialam.controller.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.trangialam.dto.GetAllContactRequest;
import com.trangialam.dto.GetAllUserRequest;
import com.trangialam.dto.PagingRequest;
import com.trangialam.model.Contact;
import com.trangialam.model.User;
import com.trangialam.service.IServiceAdmin;
import com.trangialam.service.ServiceAdminImpl;

@WebServlet("/admin/PagingContactController")
public class PagingContactController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IServiceAdmin serviceAdmin = new ServiceAdminImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setContentType("text/html;charset=UTF-8");
		int pageNumber = 1;
		HttpSession session = req.getSession();
		if (req.getParameter("pageNumber") != null) {
			pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
			session.setAttribute("pageNumberAdminContact", pageNumber);
		}
		PagingRequest pagingRequest = new PagingRequest();
		pagingRequest.setPageNumber(pageNumber);
		pagingRequest.setRecordsPerPage(6);
		
		GetAllContactRequest allContactRequest = new GetAllContactRequest();
		allContactRequest.setListContacts(serviceAdmin.getContactsPaging(pagingRequest));
		
		try {
			PrintWriter out = resp.getWriter();
			for (Contact contact : allContactRequest.getListContacts()) {
				out.println("<tr>" + 
						"												<th>"+contact.getId()+"</th>" + 
						"												<th>" +contact.getName()+"</th>" + 
						"												<th>"+contact.getEmail()+"</th>" + 
						"												<th>"+contact.getSubject()+"</th>" + 
																	 
						"												<th><table><tr><td>" + 
						"												<form class=\"btn-toolbar\" action=\"DetailContactController\"><input type='hidden' name=\"contactId\"" + 
						"													value=\""+contact.getId()+"\" /><input type=\"submit\"" + 
						"													class=\"btn btn-primary mr-1\" id=\"buttonEdit\" value=\"Detail\" /></form></td>" + 
						"													<td>\r\n" + 
						"												<form class=\"btn-toolbar\" action=\"ProcessContactController\"><input type='hidden' name=\"contactId\"" + 
						"													value=\""+contact.getId()+"\" /><input type=\"submit\"" + 
						"													class=\"btn btn-primary mr-1\" id=\"buttonEdit\" value=\"Process\" /></form></td>\r\n" + 
						"													<td><form class=\"btn-toolbar\" action=\"DeleteContactController\"><input type='hidden' name=\"contactId\"" + 
						"													value=\""+contact.getId()+"\" /><input\r\n" + 
						"													type=\"submit\" class=\"btn btn-warning\" id=\"buttonEdit\"" + 
						"													value=\"Delete\" /></form></td></tr></table>" + 
						"												</th>" + 
						"													" + 
						"											</tr>");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
