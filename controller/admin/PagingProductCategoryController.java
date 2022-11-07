package com.trangialam.controller.admin;

import java.io.IOException;
import java.io.PrintWriter;

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

@WebServlet("/admin/PagingProductCategoryAdminController")
public class PagingProductCategoryController extends HttpServlet{

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
		int categoryId = Integer.parseInt(req.getParameter("categoryId"));
		HttpSession session = req.getSession();

		if (req.getParameter("pageNumber") != null) {
			pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
			session.setAttribute("pageNumberProductCategory", pageNumber);
		}
		PagingRequest pagingRequest = new PagingRequest();
		pagingRequest.setPageNumber(pageNumber);
		pagingRequest.setRecordsPerPage(2);
		
		GetAllProductRequest allProductRequest = new GetAllProductRequest();
		allProductRequest.setListProduct(serviceAdmin.getProductCategoryPaging(pagingRequest, categoryId));
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
						"												<th>"+p.toStringDiscount()+"</th>" + 
						"												<th>"+p.getQuantity()+"</th>" + 
						"												<th><table>" + 
						"														<tr>" + 
						"															<td>" + 
						"																<form class=\"btn-toolbar\"" + 
						"																	action=\"EditProductController\">" + 
						"																	<input type='hidden' name=\"productId\"" + 
						"																		value=\""+p.getId()+"\" /><input type=\"submit\"" + 
						"																		class=\"btn btn-primary mr-1\" id=\"buttonEdit\"" + 
						"																		value=\"Edit\" />" + 
						"																</form>" + 
						"															</td>" + 
						"															<td>" + 
						"																<form class=\"btn-toolbar\"" + 
						"																	action=\"AssignProductController\">" + 
						"																	<input type='hidden' name=\"productId\"" + 
						"																		value=\""+p.getId()+"\" /><input type=\"submit\"" + 
						"																		class=\"btn btn-primary mr-1\" id=\"buttonEdit\"" + 
						"																		value=\"Assign\" />" + 
						"																</form>" + 
						"															</td>" + 
						"															<td><form class=\"btn-toolbar\"" + 
						"																	action=\"DeleteProductController\">" + 
						"																	<input type='hidden' name=\"productId\"" + 
						"																		value=\""+p.getId()+"\" /><input type=\"submit\"" + 
						"																		class=\"btn btn-warning\" id=\"buttonEdit\" value=\"Delete\" />" + 
						"																</form></td>" + 
						"														</tr>" + 
						"													</table></th>" + 
						 
						"											</tr>" + 
						"");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
