package com.trangialam.controller.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.trangialam.model.Item;
import com.trangialam.model.Order;
import com.trangialam.model.Product;
import com.trangialam.service.IServiceWeb;
import com.trangialam.service.ServiceWebImpl;

@WebServlet("/home/RemoveCartController")
public class RemoveCartController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	IServiceWeb serviceWeb = new ServiceWebImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		int productId = Integer.parseInt(req.getParameter("productId"));

		HttpSession session = req.getSession();

		Order order = (Order) session.getAttribute("order");
		List<Item> listItems = order.getItems();

		int index = indexItemInList(listItems, productId);
		listItems.remove(index);
		order.setItems(listItems);
		order.setPrice(priceOrder(listItems));
		session.setAttribute("order", order);
		
		PrintWriter out = resp.getWriter();
		out.print(order.getItems().size());

	}

	private BigDecimal priceOrder(List<Item> listItems) {
		// TODO Auto-generated method stub
		double totalPrice = 0;
		for (Item item : listItems) {
			totalPrice += item.getQuantity() * item.getPrice().doubleValue();
		}
		return new BigDecimal(totalPrice);

	}

	private int indexItemInList(List<Item> listItems, int productId) {

		for (int i = 0; i < listItems.size(); i++) {
			if (listItems.get(i).getProduct().getId() == productId) {
				return i;
			}

		}

		return -1;
	}

}
