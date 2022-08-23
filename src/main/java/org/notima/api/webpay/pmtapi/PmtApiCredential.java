package org.notima.api.webpay.pmtapi;

public class PmtApiCredential {

	public static final String DEFAULT_URL = "https://paymentadminapi.svea.com";
	
	private String	server;
	private String	merchantId;
	private String	secret;
	
	public static PmtApiCredential buildEmptyDefaultCredential() {
		PmtApiCredential credential = new PmtApiCredential();
		credential.server = DEFAULT_URL;
		credential.merchantId = "";
		credential.secret = "";
		return credential;
	}
	
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	
	
}