package se.sveaekonomi.webpay.pmtapi.test;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.sveaekonomi.pmtapi.entity.Order;
import se.sveaekonomi.webpay.pmtapi.PmtApiClientRF;
import se.sveaekonomi.webpay.pmtapi.PmtApiUtil;

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
			
			String jsonOrder = client.getOrder(154234L);
			
			Order order = PmtApiUtil.gson.fromJson(jsonOrder, Order.class);
			System.out.println("Merchant order ID: " + order.getMerchantOrderId());
			System.out.println("Order has " + order.getOrderRows().size() + " lines.");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			fail(e.getMessage());
			
		}
		
	}
		



}
