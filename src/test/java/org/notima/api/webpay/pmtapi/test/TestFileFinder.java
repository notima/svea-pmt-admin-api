package org.notima.api.webpay.pmtapi.test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.slf4j.Logger;

public class TestFileFinder {

	public static Logger log = org.slf4j.LoggerFactory.getLogger(TestFileFinder.class);	
	
	private String TEST_CONFIG = "config-test.xml";
	private String TEST_CONFIG_TEMPLATE = "config-template.xml";
	private String TEST_PROPERTY_FILE = "test-details.properties";
	
	private String TEST_ORDER_SUITE_SAMPLE = "test-order-suite-sample.json";
	private String TEST_ORDER_SUITE = "test-order-suite.json";
	
	private String FILE_PREFIX = "src" + File.separator + "test" + File.separator + "resources";

	private Properties testProperties;

	
	public String getTestConfigFile() {
		
		URL url = ClassLoader.getSystemResource(TEST_CONFIG);
		
		if (url==null) {
			log.warn("The file " + TEST_CONFIG + "  must exist in classpath for unit tests to be relevant.\n" +
				 "Copy the file " + TEST_CONFIG_TEMPLATE + " in " + FILE_PREFIX + " to " + TEST_CONFIG + " and fill in login details.");
			return TEST_CONFIG_TEMPLATE;
		}

		return TEST_CONFIG;
		
	}
	
	
	public Properties getTestProperties() throws IOException {

		testProperties = new Properties();
		
		// Find test properties file
		URL url = ClassLoader.getSystemResource(TEST_PROPERTY_FILE);
		if (url==null) {
			log.warn("The property file " + TEST_PROPERTY_FILE + " must exist in classpath for unit tests to work.\n" +
				 "Copy the file test-details-template.properties in " + FILE_PREFIX + " to " + TEST_PROPERTY_FILE + " and fill in order details."
				);
			return testProperties;
		}
		testProperties.load(new FileReader(url.getFile()));
		return testProperties;
		
	}
	
	public boolean hasOrderSuiteTestSample() {
		
		URL url = ClassLoader.getSystemResource(TEST_ORDER_SUITE_SAMPLE);
		return (url!=null);
		
	}

	public String getOrderSuiteTestSampleFile() {
		URL url = ClassLoader.getSystemResource(TEST_ORDER_SUITE_SAMPLE);
		if (url!=null)
			return url.getFile();
		else
			return FILE_PREFIX +  File.separator + TEST_ORDER_SUITE_SAMPLE;
		
	}
	
	public String getOrderSuiteTestCustomFile() {
		URL url = ClassLoader.getSystemResource(TEST_ORDER_SUITE);
		if (url!=null) {
			return url.getFile();
		}
		else
			return FILE_PREFIX +  File.separator + TEST_ORDER_SUITE;
		
	}
	
	
	public boolean hasOrderSuiteCustomTests() {
		
		URL url = ClassLoader.getSystemResource(TEST_ORDER_SUITE);
		return (url!=null);
		
	}

	
	
	
}
