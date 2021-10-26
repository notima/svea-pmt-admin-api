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

	// Store the merchant ID since it's good to have if a store has multiple merchant ids.
	private String merchantId;
	private Order order;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public boolean hasDeliveries() {
		return order!=null && order.getDeliveries()!=null && order.getDeliveries().size()>0;
	}
	
}
