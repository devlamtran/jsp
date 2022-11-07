package com.trangialam.controller.user;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.paypal.base.rest.PayPalRESTException;
import com.trangialam.dto.OrderDetailRequest;
import com.trangialam.model.User;
import com.trangialam.service.IServiceWeb;
import com.trangialam.service.PaymentServices;
import com.trangialam.service.ServiceWebImpl;


@WebServlet("/home/PaymentController")
public class PaymentController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private String clientID = "AQmO0Z_PT_ILlV4jm8RbdBIMX-h-5wkygqoZwXnPpC4OnJkdQg-KHaPRzHjppmXFD6ffHuPUw05RCVL1";
//	private String clientSecret = "ENkGmE7aJsLiTmkDHqxt7cm9d92EojsYMcbo4V9QVQ9PBMxRSp7CUezW8pYZf0ddJMnT0gdmGYCvX7by";
	private IServiceWeb serviceWeb = new ServiceWebImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		int orderId = Integer.parseInt(req.getParameter("orderId"));
		OrderDetailRequest orderDetailRequest = new OrderDetailRequest();
		orderDetailRequest.setOrder(serviceWeb.getOrder(orderId));;
		try {
            PaymentServices paymentServices = new PaymentServices();
            String approvalLink = paymentServices.authorizePayment(orderDetailRequest.getOrder(),user);
 
            resp.sendRedirect(approvalLink);
             
        } catch (PayPalRESTException ex) {
            req.setAttribute("errorMessage", ex.getMessage());
            ex.printStackTrace();
            RequestDispatcher rd = req.getRequestDispatcher("/views/web/error.jsp");
    		rd.forward(req, resp);
        }
	}
	

}
