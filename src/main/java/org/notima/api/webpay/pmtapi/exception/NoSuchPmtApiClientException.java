package org.notima.api.webpay.pmtapi.exception;

/**
 * If a client is requested but none with that identifier exists, this exception is thrown.
 * 
 * @author Daniel Tamm
 *
 */
public class NoSuchPmtApiClientException extends Exception {

	private String	identifier;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7569337307978402473L;

	public NoSuchPmtApiClientException(String identifier) {
		this.identifier = identifier;
	}
	
	public String toString() {
		return (identifier);
	}
	
}
