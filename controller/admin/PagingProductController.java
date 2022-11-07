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

import com.trangialam.dto.GetAllProductRequest;
import com.trangialam.dto.PagingRequest;
import com.trangialam.model.Product;
import com.trangialam.service.IServiceAdmin;
import com.trangialam.service.ServiceAdminImpl;

@WebServlet("/admin/PagingProductAdminController")
public class PagingProductController extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IServiceAdmin serviceAdmin = new ServiceAdminImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType("text/html;charset=UTF-8");
		int pageNumber = 1;
		HttpSession session = req.getSession();
		if (req.getParameter("pageNumber") != null) {
			pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
			session.setAttribute("pageNumberAdminProduct", pageNumber);
		}
		PagingRequest pagingRequest = new PagingRequest();
		pagingRequest.setPageNumber(pageNumber);
		pagingRequest.setRecordsPerPage(6);
		
		GetAllProductRequest allProductRequest = new GetAllProductRequest();
		allProductRequest.setListProduct(serviceAdmin.getProductsPaging(pagingRequest));
		try {
			PrintWriter out = resp.getWriter();
			for (Product p : allProductRequest.getListProduct()) {
				out.println("<tr>" + 
						"												<th>"+p.getId()+"</th>" + 
						"												<th><img width=\"80\"" + 
						"													src=\"/JSPDemo/products/"+p.getLinkImage()+"\">" + 
						 
						"												</th>" + 
						"												<th>"+p.getTitle()+"</th>" + 
						"												<th>"+p.toStringPrice()+" ð</th>" + 
						"												<th>" +p.toStringDiscount()+" ð</th>" + 
						"												<th>" +p.getQuantity()+"</th>" + 
						"												<th><table><tr><td>" + 
						"												<form class=\"btn-toolbar\" action=\"EditProductController\"><input type='hidden' name=\"productId\"" + 
						"													value=\""+p.getId()+"\" /><input type=\"submit\"\r\n" + 
						"													class=\"btn btn-primary mr-1\" id=\"buttonEdit\" value=\"Edit\" /></form></td>" + 
						"													<td>" + 
						"												<form class=\"btn-toolbar\" action=\"AssignProductController\"><input type='hidden' name=\"productId\"" + 
						"													value=\""+p.getId()+"\" /><input type=\"submit\"" + 
						"													class=\"btn btn-primary mr-1\" id=\"buttonEdit\" value=\"Assign\" /></form></td>" + 
						"													<td><form class=\"btn-toolbar\" action=\"DeleteProductController\"><input type='hidden' name=\"productId\"" + 
						"													value=\""+p.getId()+"\" /><input" + 
						"													type=\"submit\" class=\"btn btn-warning\" id=\"buttonEdit\"" + 
						"													value=\"Delete\" /></form></td></tr></table></th>" + 
						"													" + 
						"											</tr>");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	

}
