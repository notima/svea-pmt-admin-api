package org.notima.svea.pmtapi.test;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.notima.svea.pmtapi.PmtApiClientRF;

public class TestDeliverOrder {

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
			
			String result = client.deliverCompleteOrder(183315L);
			
			System.out.println(result);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			fail(e.getMessage());
			
		}
		
	}
		



}
