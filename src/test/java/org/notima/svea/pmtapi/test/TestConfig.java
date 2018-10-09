package org.notima.svea.pmtapi.test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Below is Svea Ekonomi's official test details.
 * 
 * @author Daniel Tamm
 *
 */
public class TestConfig {

	public static Logger testLogger = Logger.getLogger("svea-pmt-admin-tests");
	
	public static String SERVER = "https://paymentadminapistage.svea.com";
	public static String MERCHANT_ID = "124842";
	public static String SECRET_WORD = "1NDxpT2WQ4PW6Ud95rLWKD98xVr45Q8O9Vd52nomC7U9B18jp7lHCu7nsiTJO1NWXjSx26vE41jJ4rul7FUP1cGKXm4wakxt3iF7k63ayleb1xX9Di2wW46t9felsSPW";
	public static String CHECKOUT_ORDER_ID = "1111111";
	public static String ARTICLE_TO_DELIVER;

	public static String CREDENTIALS_FILE = "test-credentials.properties";
	
	public static Long	checkoutOrderId = null;
	
	static {
		
		// Check if there are test credentials to use
		URL url = ClassLoader.getSystemResource(CREDENTIALS_FILE);

		if (url!=null) {
			// Try to read properties
			Properties props = new Properties();
			try {
				props.load(new FileReader(url.getFile()));
				
				SERVER = props.getProperty("server", SERVER);
				MERCHANT_ID = props.getProperty("merchantId", MERCHANT_ID);
				SECRET_WORD = props.getProperty("secretWord", SECRET_WORD);
				CHECKOUT_ORDER_ID = props.getProperty("checkoutOrderId", CHECKOUT_ORDER_ID);
				
				try {
					checkoutOrderId = Long.parseLong(CHECKOUT_ORDER_ID);
				} catch (Exception e) {
					checkoutOrderId = null;
				}

				ARTICLE_TO_DELIVER = props.getProperty("articleToDeliver");
				
				testLogger.info("Test credentials supplied in file " + url.getFile());
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
