package org.notima.api.webpay.pmtapi.test;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;
import org.notima.api.webpay.pmtapi.CheckoutOrder;
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
		client.init(
				testSuite.getServer(), 
				testSuite.getMerchantId(), 
				testSuite.getSecret()
				);
	}
	
	private void runTestCase(OrderTestCase otc) {
		
		CheckoutOrder order;
		try {
			order = client.getCheckoutOrder(otc.getOrderId());
		
			if (order==null || order.getOrder()==null) {
				fail("No such order: " + otc.getOrderId());
				return;
			}
			
			System.out.println(JsonUtil.gson.toJson(order));

		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}

}
