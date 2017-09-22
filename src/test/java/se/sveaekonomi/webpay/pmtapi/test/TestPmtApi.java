package se.sveaekonomi.webpay.pmtapi.test;

import static org.junit.Assert.fail;

import java.net.URL;

import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.sveaekonomi.webpay.pmtapi.PmtApiClientRF;
import se.sveaekonomi.webpay.pmtapi.PmtApiUtil;

public class TestPmtApi {

	private Configurations configs = new Configurations();	
	private String	secretWord;
	private String	merchantId;
	private String	base64message;
	
	@Before
	public void setUp() throws Exception {
		

		URL url = ClassLoader.getSystemResource("config-template.xml");
		XMLConfiguration fc = configs.xml(url);

		secretWord = fc.getString("secretWord");
		merchantId = fc.getString("merchantId");
		
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testGetAuthHeader() {
		try {
			String ts = PmtApiUtil.getTimestampStr();
			
			String mac = PmtApiUtil.calculateAuthHeader(merchantId, base64message, secretWord, ts);
			System.out.println("Mac: " + mac);
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void testGetOrder() {

		PmtApiClientRF client = new PmtApiClientRF();
		try {
		
			client.loadConfig("config-test.xml");
			client.init();
			System.out.println("getOrder: " + client.getOrder(1245738L));
			
		} catch (Exception e) {
			
			e.printStackTrace();
			fail(e.getMessage());
			
		}
		
	}
		



}
