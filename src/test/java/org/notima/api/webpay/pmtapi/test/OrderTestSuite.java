package org.notima.api.webpay.pmtapi.test;

import java.util.ArrayList;
import java.util.List;

import org.notima.api.webpay.pmtapi.PmtApiCredential;

public class OrderTestSuite {

	private List<PmtApiCredential>	credentials;
	private List<OrderTestCase>		orderTestCases;

	public void addTestCase(OrderTestCase otc) {
		if (orderTestCases==null) {
			orderTestCases = new ArrayList<OrderTestCase>();
		}
		orderTestCases.add(otc);
	}
	
	public List<PmtApiCredential> getCredentials() {
		return credentials;
	}

	public void setCredentials(List<PmtApiCredential> credentials) {
		this.credentials = credentials;
	}

	public void addCredential(PmtApiCredential credential) {
		if (credentials==null) {
			credentials = new ArrayList<PmtApiCredential>();
		}
		credentials.add(credential);
	}
	
	public List<OrderTestCase> getOrderTestCases() {
		return orderTestCases;
	}

	public void setOrderTestCases(List<OrderTestCase> orderTestCases) {
		this.orderTestCases = orderTestCases;
	}
	
	
	
}
