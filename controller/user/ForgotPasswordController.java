package com.trangialam.controller.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trangialam.service.IServiceWeb;
import com.trangialam.service.ServiceWebImpl;
import com.trangialam.utilities.EmailUtility;

@WebServlet("/home/ForgotPasswordController")
public class ForgotPasswordController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IServiceWeb serviceWeb = new ServiceWebImpl();
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
		RequestDispatcher rd = req.getRequestDispatcher("/views/web/reset_password.jsp");
		rd.forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String recipient = req.getParameter("email");
        String subject = "Your Password has been reset";
 
        
        String newPassword = serviceWeb.resetCustomerPassword(recipient);
 
        String content = "Hi, this is your new password: " + newPassword;
        content += "\nNote: for security reason, "
                + "you must change your password after logging in.";
 
        String message = "";
 
        try {
            EmailUtility.sendEmail(host, port, email, name, pass,
                    recipient, subject, content);
            message = "Your password has been reset. Please check your e-mail.";
        } catch (Exception ex) {
            ex.printStackTrace();
            message = "There were an error: " + ex.getMessage();
        } finally {
            req.setAttribute("message", message);
            req.getRequestDispatcher("/views/web/message.jsp").forward(req, resp);
        }
	}

}
