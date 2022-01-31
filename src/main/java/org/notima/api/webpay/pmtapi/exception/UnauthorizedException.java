package org.notima.api.webpay.pmtapi.exception;

public class UnauthorizedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3217964880109562207L;

	public UnauthorizedException() {
		super();
	}
	
	public UnauthorizedException(String merchantId) {
		super("Incorrect credentials for merchantId " + merchantId);
	}
	
}
