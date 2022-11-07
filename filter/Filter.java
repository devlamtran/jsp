package com.trangialam.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.trangialam.model.Role;
import com.trangialam.model.User;
import com.trangialam.service.IServiceWeb;
import com.trangialam.service.ServiceWebImpl;

/**
 * Servlet Filter implementation class Filter
 */
public class Filter implements javax.servlet.Filter {

    /**
     * Default constructor. 
     */
	private IServiceWeb serviceWeb;
    public Filter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		User customer = null;
		List<Role> listRoleUser = null;
		if (session.getAttribute("user") != null) {
			customer = (User) session.getAttribute("user");
			listRoleUser = serviceWeb.getListRoles(customer);
			customer.setListRole(listRoleUser);

		}
		
		if (customer != null && req.getServletPath().contains("admin")) {
			if (!listRoleIsContainAdminRole(customer.getListRole())) {
				resp.sendRedirect("/JSPDemo/views/web/forbidden.jsp");
				return;
			}
			
		}else {
			if (customer == null && req.getServletPath().contains("home")) {
				//System.out.println("ok1");
				if (req.getServletPath().contains("CheckoutController")||req.getServletPath().contains("OrderController")||req.getServletPath().contains("DetailOrderController")) {
					resp.sendRedirect("/JSPDemo/home/LoginController");
					return;
				}
						
			}
			if (customer == null && req.getServletPath().contains("admin")) {
				//System.out.println("ok1");		
					resp.sendRedirect("/JSPDemo/home/LoginController");
					return;	
				
			}	
			
		}
		chain.doFilter(request, response);
		
	}

	private boolean listRoleIsContainAdminRole(List<Role> listRole) {
		boolean isCotain = false;
		for (Role role : listRole) {
			if (role.getName().equalsIgnoreCase("Admin")) {
				isCotain = true;
			}
		}
		return isCotain;
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		this.serviceWeb = new ServiceWebImpl();
	}

}
