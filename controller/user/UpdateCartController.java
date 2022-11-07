package com.trangialam.controller.user;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
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

@WebServlet("/home/UpdateCartController")
public class UpdateCartController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	IServiceWeb serviceWeb = new ServiceWebImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		int quantity = Integer.valueOf(req.getParameter("quantity"));
		int productId = Integer.parseInt(req.getParameter("productId"));

		HttpSession session = req.getSession();
		Order order = (Order) session.getAttribute("order");
		List<Item> listItems = order.getItems();
		int index = indexItemInList(listItems, productId);
		Item item = listItems.get(index);
		item.setQuantity(quantity);
		listItems.remove(index);
		listItems.add(index, item);
		order.setItems(listItems);
		order.setPrice(priceOrder(listItems));
		session.setAttribute("order", order);

	

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
