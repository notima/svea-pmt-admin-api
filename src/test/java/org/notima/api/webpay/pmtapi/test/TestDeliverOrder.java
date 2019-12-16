package org.notima.api.webpay.pmtapi.test;

import static org.junit.Assert.fail;

import org.junit.Test;

public class TestDeliverOrder extends TestBase {
	
	@Test
	public void testGetOrder() {

		try {
			
			String orderIdStr = testProperties.getProperty("test.deliverOrderId");			
			String result = client.deliverCompleteOrder(Long.parseLong(orderIdStr));
			
			log.info(result);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			fail(e.getMessage());
			
		}
		
	}
		



}
