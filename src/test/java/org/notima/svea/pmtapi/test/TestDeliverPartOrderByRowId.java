package org.notima.svea.pmtapi.test;

import static org.junit.Assert.fail;

import java.util.List;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.notima.svea.pmtapi.PmtApiClientRF;
import org.notima.svea.pmtapi.entity.OrderRow;

public class TestDeliverPartOrderByRowId {

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

			if (TestConfig.checkoutOrderId==null) {
				fail("No valid checkout order id specified in test-credentials.properties");
			}
			
			List<OrderRow> deliverRows = new ArrayList<OrderRow>();
			OrderRow or = new OrderRow();
			or.setOrderRowId(1L);	// First row only
			deliverRows.add(or);
			
			String result = client.deliverOrder(TestConfig.checkoutOrderId, deliverRows);
			
			System.out.println(result);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			fail(e.getMessage());
			
		}
		
	}
		



}
