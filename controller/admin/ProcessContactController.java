package com.trangialam.controller.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trangialam.dto.CategoryDetailRequest;
import com.trangialam.dto.ContactDetailRequest;
import com.trangialam.dto.EditProductRequest;
import com.trangialam.service.IServiceAdmin;
import com.trangialam.service.IServiceWeb;
import com.trangialam.service.ServiceAdminImpl;
import com.trangialam.service.ServiceWebImpl;
import com.trangialam.utilities.EmailUtility;

@WebServlet("/admin/ProcessContactController")
public class ProcessContactController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IServiceAdmin serviceAdmin = new ServiceAdminImpl();
	private String host;
	private String port;
	private String email;
	private String name;
	private String pass;

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		ServletContext context = getServletContext();
		host = context.getInitParameter("host");
		port = context.getInitParameter("port");
		email = context.getInitParameter("email");
		name = context.getInitParameter("name");
		pass = context.getInitParameter("pass");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int contactId = Integer.valueOf(req.getParameter("contactId"));
		ContactDetailRequest  contactDetailRequest = new ContactDetailRequest();
		contactDetailRequest.setContact(serviceAdmin.getContactById(contactId));;
		req.setAttribute("contactDetailRequest", contactDetailRequest);
		
		RequestDispatcher rd = req.getRequestDispatcher("/views/admin/processContact.jsp");
		rd.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String recipient = req.getParameter("email");
        String subject = req.getParameter("subject");
        String content =  req.getParameter("content");
 
        try {
            EmailUtility.sendEmail(host, port, email, name, pass,
                    recipient, subject, content);
            
        } catch (Exception ex) {
            ex.printStackTrace();
            
        } finally {
           
            req.getRequestDispatcher("/views/admin/contactIndex.jsp").forward(req, resp);
        }
	}

}
