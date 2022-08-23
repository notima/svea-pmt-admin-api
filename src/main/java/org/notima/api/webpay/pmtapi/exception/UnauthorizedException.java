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
		super(createMessage(merchantId, null));
	}
	
	public UnauthorizedException(String merchantId, String secret) {
		super(createMessage(merchantId, secret));
	}
	
	private static String createMessage(String merchantId, String secret) {
		return "Incorrect credentials for merchantId " + merchantId + " and secret " + obscureSecret(secret);
	}
	
	private static String obscureSecret(String secret) {
		if (secret==null) 
			return "<empty>";
		
		if (secret.length()>10) {
			return secret.substring(0,5) + "..." + secret.substring(secret.length()-5);
		} else {
			return secret.substring(0,1) + "*****";
		}
		
	}
	
}
