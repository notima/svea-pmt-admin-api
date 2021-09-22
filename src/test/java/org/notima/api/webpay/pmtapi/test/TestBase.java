package org.notima.api.webpay.pmtapi.test;

import static org.junit.Assert.fail;

import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.notima.api.webpay.pmtapi.PmtApiClientRF;
import org.slf4j.Logger;

public class TestBase {

	public static Logger log = org.slf4j.LoggerFactory.getLogger(TestBase.class);

	protected TestFileFinder testFileFinder = new TestFileFinder();
	protected Properties testProperties;
	protected PmtApiClientRF client = new PmtApiClientRF();
	
	@Before
	public void setUp() throws Exception {

		try {
			
			client.loadConfig(testFileFinder.getTestConfigFile());
			client.init();
			
			testProperties = testFileFinder.getTestProperties();
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			fail(e.getMessage());
			
		}
		
	}

	@After
	public void tearDown() throws Exception {
	}


}
