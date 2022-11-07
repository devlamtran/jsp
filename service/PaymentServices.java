package com.trangialam.service;

import java.util.ArrayList;
import java.util.List;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.trangialam.model.Order;
import com.trangialam.model.User;

public class PaymentServices {
	
	 private static final String CLIENT_ID = "AQmO0Z_PT_ILlV4jm8RbdBIMX-h-5wkygqoZwXnPpC4OnJkdQg-KHaPRzHjppmXFD6ffHuPUw05RCVL1";
	    private static final String CLIENT_SECRET = "ENkGmE7aJsLiTmkDHqxt7cm9d92EojsYMcbo4V9QVQ9PBMxRSp7CUezW8pYZf0ddJMnT0gdmGYCvX7by";
	    private static final String MODE = "sandbox";
	 
	    public String authorizePayment(Order order, User customer)        
	            throws PayPalRESTException {       
	 
	        Payer payer = getPayerInformation(customer);
	        RedirectUrls redirectUrls = getRedirectURLs();
	        List<Transaction> listTransaction = getTransactionInformation(order);
	         
	        Payment requestPayment = new Payment();
	        requestPayment.setTransactions(listTransaction);
	        requestPayment.setRedirectUrls(redirectUrls);
	        requestPayment.setPayer(payer);
	        requestPayment.setIntent("authorize");
	 
	        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
	 
	        Payment approvedPayment = requestPayment.create(apiContext);
	 
	        return getApprovalLink(approvedPayment);
	 
	    }
	     
	    private Payer getPayerInformation(User customer) {
	    	
	    	 Payer payer = new Payer();
	    	 payer.setPaymentMethod("paypal");
	    	     
	    	 PayerInfo payerInfo = new PayerInfo();
	    	 payerInfo.setFirstName(customer.getFirstName())
	    	             .setLastName(customer.getLastName())
	    	             .setEmail(customer.getEmail());
	    	     
	    	    payer.setPayerInfo(payerInfo);
	    	   System.out.println(payerInfo.getLastName());  
	    	    return payer;
	        
	    }
	     
	    private RedirectUrls getRedirectURLs() {
	    	RedirectUrls redirectUrls = new RedirectUrls();
	        redirectUrls.setCancelUrl("http://localhost:8080/JSPDemo/home/cancel.jsp");
	        redirectUrls.setReturnUrl("http://localhost:8080/JSPDemo/home/review_payment");
	         System.out.println("xong url");
	        return redirectUrls;
	    }
	     
	    private List<Transaction> getTransactionInformation(Order order) {
	    	Details details = new Details();
	        details.setShipping(order.getCity());
	        details.setSubtotal(order.toStringPrice());
	        details.setTax("10");
	        
	        System.out.println(details.getSubtotal());
	        Amount amount = new Amount();
	        amount.setCurrency("USD");
	        amount.setTotal(order.toStringPrice());
	        amount.setDetails(details);
	     
	        Transaction transaction = new Transaction();
	        transaction.setAmount(amount);
	        transaction.setDescription(order.getCountry());
	         
	        ItemList itemList = new ItemList();
	        List<Item> items = new ArrayList<Item>();
	        List<Transaction> listTransaction = new ArrayList<Transaction>();
	        for (int i = 0; i < order.getItems().size(); i++) {
	        	
	        	Item item = new Item();
		        item.setCurrency("USD");
		        item.setName(order.getItems().get(i).getProduct().getTitle());
		        item.setPrice(order.getItems().get(i).toStringPrice());
		        item.setTax("10");
		        item.setQuantity(" "+order.getItems().get(i).getQuantity());
		         
		        items.add(item);
		        
		        
			}
	        itemList.setItems(items);
	        transaction.setItemList(itemList);
	     
	        listTransaction.add(transaction); 
	         
	        return listTransaction;
	    }
	     
	    private String getApprovalLink(Payment approvedPayment) {
	    	List<Links> links = approvedPayment.getLinks();
	        String approvalLink = null;
	         
	        for (Links link : links) {
	            if (link.getRel().equalsIgnoreCase("approval_url")) {
	                approvalLink = link.getHref();
	                break;
	            }
	        }      
	         
	        return approvalLink;
	    }
	    public Payment getPaymentDetails(String paymentId) throws PayPalRESTException {
	        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
	        return Payment.get(apiContext, paymentId);
	    }

}
