package com.trangialam.controller.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trangialam.dto.GetAllProductSearchRequest;
import com.trangialam.dto.PagingRequest;
import com.trangialam.model.Product;
import com.trangialam.service.IServiceWeb;
import com.trangialam.service.ServiceWebImpl;

@WebServlet("/home/SearchAjax")
public class SearchAjax extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private IServiceWeb serviceWeb = new ServiceWebImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		int pageNumber = 1;
	    String keyword = "";
	    if (req.getParameter("key")!=null) {
	    	keyword = req.getParameter("key");
		}
	    if (req.getParameter("pageNumber") != null) {
			pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
		}

		
		PagingRequest pagingRequest = new PagingRequest();
		pagingRequest.setPageNumber(pageNumber);
		pagingRequest.setRecordsPerPage(6);

		GetAllProductSearchRequest allProductRequest = new GetAllProductSearchRequest();
		allProductRequest.setListProduct(serviceWeb.getProductSearchPaging(pagingRequest,keyword));
		
		try {
			PrintWriter out = resp.getWriter();
			for (Product p : allProductRequest.getListProduct()) {
				out.println("<div class='col-lg-4 col-md-6 col-sm-12 pb-1'>" + 
						"                        <div class='card product-item border-0 mb-4'>" + 
						"                            <div class='card-header product-img position-relative overflow-hidden bg-transparent border p-0'>" + 
						"                                <img class='img-fluid w-100' src='/JSPDemo/products/"+p.getLinkImage()+"' alt=''>" + 
						"                            </div>" + 
						"                            <div class='card-body border-left border-right text-center p-0 pt-4 pb-3'>" + 
						"                                <h6 class='text-truncate mb-3'>"+p.getTitle()+"</h6>" + 
						"                                <div class='d-flex justify-content-center'>" + 
						"                                    <h6>"+p.toStringPrice()+"ð</h6><h6 class='text-muted ml-2'><del>"+p.toStringDiscount()+"ð</del></h6>" + 
						"                                </div>" + 
						"                            </div>" + 
						"                            <div class='card-footer d-flex justify-content-between bg-light border'>" + 
						"                                <a href='/home/DetailProductController?productId="+p.getId()+"' class='btn btn-sm text-dark p-0'><i class='fas fa-eye text-primary mr-1'></i>View Detail</a>" + 
						"                                <a href='/home/AddToCartController?productId="+p.getId()+"' class='btn btn-sm text-dark p-0 add-cart'><i class='fas fa-shopping-cart text-primary mr-1'></i>Add To Cart</a>" + 
						"                            </div>" + 
						"                        </div>" + 
						"                    </div>");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
