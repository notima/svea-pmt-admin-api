package org.notima.api.webpay.pmtapi.test;

import static org.junit.Assert.fail;

import org.junit.Test;

public class TestDeliverOrder extends TestBase {
	
	@Test
	public void testGetOrder() {

		try {
			
			String result = client.deliverCompleteOrder(183315L);
			
			System.out.println(result);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			fail(e.getMessage());
			
		}
		
	}
		



}
