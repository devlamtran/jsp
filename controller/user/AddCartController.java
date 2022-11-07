package com.trangialam.controller.user;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
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

@WebServlet("/home/AddCartController")
public class AddCartController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IServiceWeb serviceWeb = new ServiceWebImpl();


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int quantity = 1;
		int id = Integer.parseInt(req.getParameter("productId"));
		Order order = null;
		Product product = serviceWeb.getProductById(id);
		if (product != null) {
			if (req.getParameter("quantity") != null) {
				quantity = Integer.parseInt(req.getParameter("quantity"));
			}
			HttpSession session = req.getSession();
			if (session.getAttribute("order") == null) {
				order = new Order();
				List<Item> listItems = new ArrayList<Item>();
				Item item = new Item();
				item.setQuantity(quantity);
				item.setProduct(product);
				item.setPrice(product.getPrice());
				listItems.add(item);
				order.setItems(listItems);
				order.setPrice(priceOrder(listItems));
				session.setAttribute("order", order);
			} else {
				order = (Order) session.getAttribute("order");
				List<Item> listItems = order.getItems();
				boolean check = false;
				for (Item item : listItems) {
					if (item.getProduct().getId() == product.getId()) {
						item.setQuantity(item.getQuantity() + quantity);
						check = true;
					}
				}
				if (check == false) {
					Item item = new Item();
					item.setQuantity(quantity);
					item.setProduct(product);
					item.setPrice(product.getPrice());
					listItems.add(item);

				}
				order.setItems(listItems);
				order.setPrice(priceOrder(listItems));
				session.setAttribute("order", order);
			}
			resp.sendRedirect("ProductController");
			
		}
	}

	private BigDecimal priceOrder(List<Item> listItems) {

		double totalPrice = 0;
		for (Item item : listItems) {
			totalPrice += item.getQuantity() * item.getPrice().doubleValue();
		}
		return new BigDecimal(totalPrice);

	}

}
