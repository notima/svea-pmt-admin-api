package org.notima.api.webpay.pmtapi.test;

import static org.junit.Assert.fail;

import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.notima.api.webpay.pmtapi.PmtApiClientCollection;
import org.notima.api.webpay.pmtapi.PmtApiClientRF;
import org.slf4j.Logger;

public class TestBase {

	public static Logger log = org.slf4j.LoggerFactory.getLogger(TestBase.class);

	protected TestFileFinder testFileFinder = new TestFileFinder();
	protected Properties testProperties;
	protected PmtApiClientCollection	clientCollection = new PmtApiClientCollection();
	protected PmtApiClientRF client = new PmtApiClientRF();
	
	@Before
	public void setUp() throws Exception {

		try {
			
			client.loadConfig(testFileFinder.getTestConfigFile());
			
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
