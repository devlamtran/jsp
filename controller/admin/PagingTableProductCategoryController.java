package com.trangialam.controller.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trangialam.dto.GetAllProductCategoryRequest;
import com.trangialam.dto.PagingRequest;
import com.trangialam.service.IServiceAdmin;
import com.trangialam.service.ServiceAdminImpl;

@WebServlet("/admin/PagingTableProductCategoryController")
public class PagingTableProductCategoryController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IServiceAdmin serviceAdmin = new ServiceAdminImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		int categoryId = Integer.parseInt(req.getParameter("categoryId"));
		PagingRequest pagingRequest = new PagingRequest();
		pagingRequest.setPageNumber(1);
		pagingRequest.setRecordsPerPage(2);
		GetAllProductCategoryRequest allProductRequest = new GetAllProductCategoryRequest();
		allProductRequest.setListProduct(serviceAdmin.getProductCategoryPaging(pagingRequest, categoryId));
		int totalPages = serviceAdmin.getTotalPagesProductCategory(pagingRequest);
		String html = "<tr>";

		for (int i = 1; i <= totalPages; i++) {

			html += "<td><a class=\"pagination\" data-page=\"" + i + "\" href=\"/JSPDemo/admin/ProductCategoryAdminController?categoryId="+categoryId+"&pageNumber="+i+"\">" + i + "</a></td>";

		}
		html+= "</tr>";
		out.write(html);

	}

}
