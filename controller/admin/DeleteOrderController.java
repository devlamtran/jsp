package com.trangialam.controller.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trangialam.dto.DeleteOrderRequest;
import com.trangialam.dto.OrderDetailRequest;
import com.trangialam.service.IServiceAdmin;
import com.trangialam.service.ServiceAdminImpl;

@WebServlet("/admin/DeleteOrderController")
public class DeleteOrderController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IServiceAdmin serviceAdmin = new ServiceAdminImpl();

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int orderId = Integer.parseInt(req.getParameter("orderId"));

//		OrderDetailRequest orderDetailRequest = new OrderDetailRequest();
//		orderDetailRequest.setItems(serviceAdmin.getItemsInOrder(orderId));
//		req.setAttribute("listItemsOrderRequest", orderDetailRequest);
		req.setAttribute("orderId", orderId);
		
		RequestDispatcher rd = req.getRequestDispatcher("/views/admin/deleteOrder.jsp");
		rd.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int orderId = Integer.parseInt(req.getParameter("orderId"));
		DeleteOrderRequest deleteOrderRequest = new DeleteOrderRequest();
		deleteOrderRequest.setOrderId(orderId);
		if (serviceAdmin.deleleOrder(deleteOrderRequest)) {
			RequestDispatcher rd = req.getRequestDispatcher("/views/admin/orderIndex.jsp");
			rd.forward(req, resp);
		}else {
			doGet(req, resp);
		}
		
	}

}
