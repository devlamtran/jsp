package com.trangialam.controller.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;
import com.trangialam.service.PaymentServices;

@WebServlet("/home/review_payment")
public class ReviewPayment extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String paymentId = req.getParameter("paymentId");
      //  String payerId = req.getParameter("PayerID");
         
        try {
            PaymentServices paymentServices = new PaymentServices();
            Payment payment = paymentServices.getPaymentDetails(paymentId);
             
            PayerInfo payerInfo = payment.getPayer().getPayerInfo();
            Transaction transaction = payment.getTransactions().get(0);
            ShippingAddress shippingAddress = transaction.getItemList().getShippingAddress();
             
            req.setAttribute("payer", payerInfo);
            req.setAttribute("transaction", transaction);
            req.setAttribute("shippingAddress", shippingAddress);
             
            //String url = "review.jsp?paymentId=" + paymentId + "&PayerID=" + payerId;
             
            RequestDispatcher rd = req.getRequestDispatcher("/views/web/review.jsp");
    		rd.forward(req, resp);
             
        } catch (PayPalRESTException ex) {
            req.setAttribute("errorMessage", ex.getMessage());
            ex.printStackTrace();
            RequestDispatcher rd = req.getRequestDispatcher("/views/web/error.jsp");
    		rd.forward(req, resp);
        }      
	}

}
