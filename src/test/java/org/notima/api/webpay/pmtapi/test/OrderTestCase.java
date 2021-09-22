package org.notima.api.webpay.pmtapi.test;

public class OrderTestCase {

	private Long	orderId;
	private Double	expectedDeliveryAmount;
	private Double	expectedCreditAmount;
	private int		expectedNumberOfDeliveries;
	private String	expectedSystemStatus;
	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Double getExpectedDeliveryAmount() {
		return expectedDeliveryAmount;
	}
	public void setExpectedDeliveryAmount(Double expectedDeliveryAmount) {
		this.expectedDeliveryAmount = expectedDeliveryAmount;
	}
	public Double getExpectedCreditAmount() {
		return expectedCreditAmount;
	}
	public void setExpectedCreditAmount(Double expectedCreditAmount) {
		this.expectedCreditAmount = expectedCreditAmount;
	}
	public int getExpectedNumberOfDeliveries() {
		return expectedNumberOfDeliveries;
	}
	public void setExpectedNumberOfDeliveries(int expectedNumberOfDeliveries) {
		this.expectedNumberOfDeliveries = expectedNumberOfDeliveries;
	}
	public String getExpectedSystemStatus() {
		return expectedSystemStatus;
	}
	public void setExpectedSystemStatus(String expectedSystemStatus) {
		this.expectedSystemStatus = expectedSystemStatus;
	}
	
	
	
}
