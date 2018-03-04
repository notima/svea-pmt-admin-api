package org.notima.svea.pmtapi.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.notima.svea.pmtapi.PmtApiUtil;

public class TestAuthHeader {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Use hard coded test details (since we might want to change the details in TestConfig).
	 * 
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception {
		
		// Below credentials are Svea's official test credentials.
		String ts = "2017-09-25 12:24";
		String merchantId = "124842";
		String secretWord = "1NDxpT2WQ4PW6Ud95rLWKD98xVr45Q8O9Vd52nomC7U9B18jp7lHCu7nsiTJO1NWXjSx26vE41jJ4rul7FUP1cGKXm4wakxt3iF7k63ayleb1xX9Di2wW46t9felsSPW";
		String body = "";
		
		String expected = "Svea " + "MTI0ODQyOjJmZDU5NDFhNDA1MWYxMjk5OTljZTMxM2QxNDkzZWM0NzQwZjFiODFjNmZlMDQ2ZjBlNWIwNDE4OTQ3ZWJiNDQ4ZGEyMTI2ZjJmNDhmNjJkNWVkZmJkYzdiNDMxYjljZGQ4YzZhYmJjMWQyMjM3MWRkOTlhMmEzMDM5MDcyNWEz";
		
		String authMsg = PmtApiUtil.calculateAuthHeader(merchantId, body, secretWord, ts);
		
		org.junit.Assert.assertEquals("", expected, authMsg);
		
		
	}

}
