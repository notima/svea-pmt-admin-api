package org.notima.api.webpay.pmtapi;

import org.notima.api.webpay.pmtapi.entity.Order;

/**
 * Use this class to work with the checkout order. Try to avoid accessing the entities directly.
 * See this class as a wrapper class to access information about the order.
 * 
 * @author Daniel Tamm
 *
 */
public class CheckoutOrder {

	private Order order;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public boolean hasDeliveries() {
		return order!=null && order.getDeliveries()!=null && order.getDeliveries().size()>0;
	}
	
}
