package org.notima.api.webpay.pmtapi.exception;

public class InvalidAuthorizationException extends Exception {

	private String merchantId;
	private Long	orderId;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1951273931063260259L;

	public InvalidAuthorizationException(String merchantId, Long orderId) {
		super(orderId!=null ? ("MerchantId " + merchantId + " is not allowed to access " + orderId.toString()) : "No order id supplied");
		this.merchantId = merchantId;
		this.orderId = orderId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	
	
}
