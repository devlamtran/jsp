package com.trangialam.controller.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.trangialam.dto.GetAllCategoryRequest;
import com.trangialam.dto.GetAllOrderCustomerRequest;
import com.trangialam.dto.PagingRequest;
import com.trangialam.model.Order;
import com.trangialam.model.User;
import com.trangialam.service.IServiceWeb;
import com.trangialam.service.ServiceWebImpl;

@WebServlet("/home/PagingOrderController")
public class PagingOrderController extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IServiceWeb serviceWeb = new ServiceWebImpl();

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		
		HttpSession session = req.getSession();
		
		GetAllCategoryRequest allCategoryRequest = new GetAllCategoryRequest();
		allCategoryRequest.setListCategories(serviceWeb.getAllCategories());
		req.setAttribute("listCategoriesRequest", allCategoryRequest);
		
		int pageNumber = 1;
		User user = (User) session.getAttribute("user");
		int userId = user.getId();

		if (req.getParameter("pageNumber") != null) {
			pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
		}
		
		PagingRequest pagingRequest = new PagingRequest();
		pagingRequest.setPageNumber(pageNumber);
		pagingRequest.setRecordsPerPage(6);
		
		GetAllOrderCustomerRequest allOrderCustomerRequest = new GetAllOrderCustomerRequest();
		allOrderCustomerRequest.setListOrders(serviceWeb.getOrdersPaging(pagingRequest, userId));
		
		try {
			PrintWriter out = resp.getWriter();
			for (Order order : allOrderCustomerRequest.getListOrders()) {
				out.println("<tr>" + 
						"                            <td data-orderId ='"+order.getId()+"' class='align-middle'>"+order.getId()+"</td>" + 
						"                            <td class='align-middle'>"+order.getFirstName()+"</td>" + 
						"                            <td class='align-middle'>"+order.getLastName()+"</td>" + 
						"                            <td class='align-middle'>"+order.getEmail()+"</td>" + 
						"                            <td class='align-middle'>"+order.getMobile()+"</td>" + 
						"                            <td class='align-middle'>"+order.getCity()+"</td>" + 
						"                            <td class='align-middle'>"+order.toStringPrice()+" ð</td>" + 
						"                            <td class='align-middle'>"+order.getStatus()+"</td>" + 
						"                            <td class='align-middle'>"+order.getCreatedAt()+"</td>" + 
						"                            <td class='align-middle'><a class='' href='/JSPDemo/home/DetailOrderController?orderId="+order.getId()+"' class='btn btn-sm btn-primary'>Detail</a>" + 
						"                            </td>" + 
						"                        </tr>");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
