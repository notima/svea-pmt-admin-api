package org.notima.api.webpay.pmtapi.test;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.notima.api.webpay.pmtapi.PmtApiClientRF;

public class TestBase {

	protected PmtApiClientRF client = new PmtApiClientRF();
	
	
	@Before
	public void setUp() throws Exception {

		try {
			
			client.loadConfig("config-test.xml");
			client.init();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			fail(e.getMessage());
			
		}
		
	}

	@After
	public void tearDown() throws Exception {
	}


}
