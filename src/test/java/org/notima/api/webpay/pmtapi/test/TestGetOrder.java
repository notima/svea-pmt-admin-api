package org.notima.api.webpay.pmtapi.test;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.notima.api.webpay.pmtapi.CheckoutOrder;
import org.notima.api.webpay.pmtapi.util.JsonUtil;

public class TestGetOrder extends TestBase {
	
	@Test
	public void testGetOrder() {

		try {
			
			if (!client.isValid()) {
				log.warn("Client not initialized. Skipping test");
				return;
			}
			
			String orderIdStr = testProperties.getProperty("test.getOrderId", "100000");
			Long orderId = Long.parseLong(orderIdStr);
			
			CheckoutOrder order = client.getCheckoutOrder(orderId);
			
			if (order==null) {
				fail("No such order: " + orderId);
				return;
			}
			
			System.out.println(JsonUtil.gson.toJson(order));
			
		} catch (Exception e) {
			
			e.printStackTrace();
			fail(e.getMessage());
			
		}
		
	}
		



}
