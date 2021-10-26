package org.notima.api.webpay.pmtapi.exception;

public class NoSuchOrderException extends Exception {

	long orderId;
	String msg;
	String merchantId;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7391065304163678388L;

	public NoSuchOrderException(long orderId) {
		this.orderId = orderId;
	}
	
	public NoSuchOrderException(String msg) {
		this.msg = msg;
	}
	
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();
		if (merchantId!=null && merchantId.trim().length()>0) {
			buf.append("MerchantId: " + merchantId);
		}
		if (orderId!=0) {
			appendIfNecessary(buf);
			buf.append("Order not found " + orderId);
		}
		if (msg!=null && msg.trim().length()>0) {
			appendIfNecessary(buf);
			buf.append(msg);
		}
		return buf.toString();
	}
	
	private StringBuffer appendIfNecessary(StringBuffer buf) {
		if (buf.length()>0) {
			buf.append(" : ");
		}
		return buf;
	}
	
}
