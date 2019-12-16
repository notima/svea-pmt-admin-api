package org.notima.api.webpay.pmtapi.test;

import static org.junit.Assert.fail;

import java.io.FileReader;
import java.net.URL;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.notima.api.webpay.pmtapi.PmtApiClientRF;
import org.slf4j.Logger;

public class TestBase {

	public static Logger log = org.slf4j.LoggerFactory.getLogger(TestBase.class);
	
	protected String TEST_CONFIG = "config-test.xml";
	protected String TEST_PROPERTY_FILE = "test-details.properties";
	
	protected Properties testProperties = new Properties();
	
	protected PmtApiClientRF client = new PmtApiClientRF();
	
	
	@Before
	public void setUp() throws Exception {

		try {
	
			URL url = ClassLoader.getSystemResource(TEST_CONFIG);
			
			if (url==null) {
				fail("The file " + TEST_CONFIG + "  must exist in classpath for unit tests to work.\n" +
					 "Copy the file config-template.xml in src/test/resources to config-test.xml and fill in login details.");
			}
			
			client.loadConfig(TEST_CONFIG);
			client.init();
			
			// Find test properties file
			url = ClassLoader.getSystemResource(TEST_PROPERTY_FILE);
			if (url==null) {
				fail("The property file " + TEST_PROPERTY_FILE + " must exist in classpath for unit tests to work.\n" +
					 "Copy the file test-details-template.properties in src/test/resources to " + TEST_PROPERTY_FILE + " and fill in order details."
					);
			}
			testProperties.load(new FileReader(url.getFile()));
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			fail(e.getMessage());
			
		}
		
	}

	@After
	public void tearDown() throws Exception {
	}


}
