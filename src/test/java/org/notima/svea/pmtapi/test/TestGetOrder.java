package org.notima.svea.pmtapi.test;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.notima.svea.pmtapi.PmtApiClientRF;
import org.notima.svea.pmtapi.entity.Order;
import org.notima.svea.pmtapi.util.JsonUtil;

public class TestGetOrder {

	@Before
	public void setUp() throws Exception {
			
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testGetOrder() {

		PmtApiClientRF client = new PmtApiClientRF();
		try {
		
			client.init(TestConfig.SERVER, TestConfig.MERCHANT_ID, TestConfig.SECRET_WORD);
			
			Long orderId = 186694L;
			
			Order order = client.getOrder(orderId);
			
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
