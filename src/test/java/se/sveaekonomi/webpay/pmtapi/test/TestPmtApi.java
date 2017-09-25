package se.sveaekonomi.webpay.pmtapi.test;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.sveaekonomi.webpay.pmtapi.PmtApiClientRF;

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
			System.out.println("getOrder: " + client.getOrder(154234L));
			
		} catch (Exception e) {
			
			e.printStackTrace();
			fail(e.getMessage());
			
		}
		
	}
		



}
