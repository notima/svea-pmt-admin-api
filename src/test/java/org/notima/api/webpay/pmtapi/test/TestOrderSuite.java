package org.notima.api.webpay.pmtapi.test;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;
import org.notima.api.webpay.pmtapi.CheckoutOrder;
import org.notima.api.webpay.pmtapi.PmtApiCredential;
import org.notima.api.webpay.pmtapi.exception.NoSuchOrderException;
import org.notima.api.webpay.pmtapi.util.JsonUtil;

public class TestOrderSuite extends TestBase {
	
	private OrderTestSuite		testSuite;
	
	@Test
	public void testOrderSuite() {

		try {
			
			if (!testFileFinder.hasOrderSuiteTestSample()) {
				createOrderSuiteTestSample();
			}
			
			if (testFileFinder.hasOrderSuiteCustomTests()) {
				runCustomTests();
			}
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			fail(e.getMessage());
			
		}
		
	}
		
	private void createOrderSuiteTestSample() throws IOException {
		
		OrderSuiteTestSampleCreator sampleCreator = OrderSuiteTestSampleCreator.buildFromFileFinder(testFileFinder);
		sampleCreator.createSampleTestAndSaveToFile();
		log.info("Test sample file created: " + testFileFinder.getOrderSuiteTestSampleFile());
		
	}
	

	private void runCustomTests() {

		try {
			
			OrderSuiteTestReader ostr = OrderSuiteTestReader.buildFromFileFinder(testFileFinder);
			ostr.loadCustomTestsFromFile();
			
			testSuite = ostr.getOrderTestSuite();
			
			if (testSuite==null || testSuite.getOrderTestCases()==null || testSuite.getOrderTestCases().isEmpty()) {
				fail("No custom tests found.");
			}

			initClientFromTestSuite();
			
			for (OrderTestCase testCase : testSuite.getOrderTestCases()) {
				runTestCase(testCase);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}
	
	private void initClientFromTestSuite() {
		
		for (PmtApiCredential pac : testSuite.getCredentials()) {
			clientCollection.addPmtApiClient(pac);
		}
		
	}
	
	private void runTestCase(OrderTestCase otc) {
		
		CheckoutOrder order;
		try {
			
			order = clientCollection.getCheckoutOrder(otc.getOrderId());
			
			System.out.println(JsonUtil.gson.toJson(order));

		} catch (NoSuchOrderException nsoe) {
			fail("No such order: " + nsoe.toString());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}

}
