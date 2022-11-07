package com.trangialam.controller.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.trangialam.dto.GetAllOrderRequest;
import com.trangialam.dto.GetAllUserRequest;
import com.trangialam.dto.PagingRequest;
import com.trangialam.model.Order;
import com.trangialam.model.User;
import com.trangialam.service.IServiceAdmin;
import com.trangialam.service.ServiceAdminImpl;

@WebServlet("/admin/PagingOrderController")
public class PagingOrderController extends HttpServlet{

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
			session.setAttribute("pageNumberAdminOrder", pageNumber);
		}
		PagingRequest pagingRequest = new PagingRequest();
		pagingRequest.setPageNumber(pageNumber);
		pagingRequest.setRecordsPerPage(6);
		
		GetAllOrderRequest allOrderRequest = new GetAllOrderRequest();
		allOrderRequest.setListOrders(serviceAdmin.getAllOrdersPaging(pagingRequest));
		req.setAttribute("listOrdersRequest", allOrderRequest);
		
		int totalPages = serviceAdmin.getTotalPagesOrder(pagingRequest);
		req.setAttribute("totalPages", totalPages);
		req.setAttribute("currentPage", pagingRequest.getPageNumber());
		
		try {
			PrintWriter out = resp.getWriter();
			for (Order order :  allOrderRequest.getListOrders()) {
				out.println("<tr>" + 
						"												<th>"+order.getId()+"</th>" + 
						"												<th>" +order.getFirstName()+"</th>" + 
						"												<th>"+order.getLastName()+"</th>" + 
						"												<th>"+order.getEmail()+"</th>" + 
						"												<th>"+order.getMobile()+"</th>" + 
						"												<th>"+order.getCity()+"</th>" + 
						"												<th>"+order.toStringPrice()+"</th>" + 
						"                                               <th>"+order.getStatus()+"</th>" + 
						"                                                <th>"+order.getCreatedAt()+"</th>" + 
						"												<th><table><tr><td>" + 
						"												<form class=\"btn-toolbar\" action=\"DetailOrderAdminController\"><input type='hidden' name=\"orderId\"" + 
						"													value=\""+order.getId()+"\" /><input type=\"submit\"" + 
						"													class=\"btn btn-primary mr-1\" id=\"buttonEdit\" value=\"Detail\" /></form></td>" + 
																		
						"													<td><form class=\"btn-toolbar\" action=\"DeleteOrderController\"><input type='hidden' name=\"orderId\"" + 
						"													value=\""+order.getId()+"\" /><input\r\n" + 
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
