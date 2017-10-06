package se.sveaekonomi.webpay.pmtapi.test;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.sveaekonomi.webpay.pmtapi.PmtApiClientRF;
import se.sveaekonomi.webpay.pmtapi.entity.Order;

public class TestPmtApi {

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
		
			client.loadConfig("config-test.xml");
			client.init();
			
			Long orderId = 238944L;
			
			Order order = client.getOrder(orderId);
			
			if (order==null) {
				fail("No such order: " + orderId);
				return;
			}
			
			System.out.println("Merchant order ID: " + order.getMerchantOrderId());
			
		} catch (Exception e) {
			
			e.printStackTrace();
			fail(e.getMessage());
			
		}
		
	}
		



}
