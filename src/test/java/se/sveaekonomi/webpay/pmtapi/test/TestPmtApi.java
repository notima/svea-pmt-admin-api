package se.sveaekonomi.webpay.pmtapi.test;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.sveaekonomi.webpay.pmtapi.PmtApiClientRF;
import se.sveaekonomi.webpay.pmtapi.PmtApiUtil;
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
			
			Order order = client.getOrder(154233L);
			
			System.out.println("Merchant order ID: " + order.getMerchantOrderId());
			System.out.println("Order has " + order.getOrderRows().size() + " lines.");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			fail(e.getMessage());
			
		}
		
	}
		



}
