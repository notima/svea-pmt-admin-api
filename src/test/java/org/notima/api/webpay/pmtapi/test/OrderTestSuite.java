package org.notima.api.webpay.pmtapi.test;

import java.util.ArrayList;
import java.util.List;

public class OrderTestSuite {

	private String	server;
	private String	merchantId;
	private String	secret;
	
	private List<OrderTestCase>		orderTestCases;

	public void addTestCase(OrderTestCase otc) {
		if (orderTestCases==null) {
			orderTestCases = new ArrayList<OrderTestCase>();
		}
		orderTestCases.add(otc);
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
	
	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public List<OrderTestCase> getOrderTestCases() {
		return orderTestCases;
	}

	public void setOrderTestCases(List<OrderTestCase> orderTestCases) {
		this.orderTestCases = orderTestCases;
	}
	
	
	
}
