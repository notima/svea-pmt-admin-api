package org.notima.api.webpay.pmtapi.test;

import java.io.FileWriter;
import java.io.IOException;

import org.notima.api.webpay.pmtapi.PmtApiCredential;
import org.notima.api.webpay.pmtapi.util.JsonUtil;

public class OrderSuiteTestSampleCreator {

	private TestFileFinder	testFileFinder;
	private OrderTestSuite suite;
	
	public static OrderSuiteTestSampleCreator buildFromFileFinder(TestFileFinder testFileFinder) {
		
		OrderSuiteTestSampleCreator creator = new OrderSuiteTestSampleCreator();
		creator.testFileFinder = testFileFinder;
		return creator;
		
	}

	public void createSampleTestAndSaveToFile() throws IOException {
		
		suite = new OrderTestSuite();
		suite.addCredential(PmtApiCredential.buildEmptyDefaultCredential());
		
		OrderTestCase orderTestCase = new OrderTestCase();
		orderTestCase.setOrderId(1L);
		suite.addTestCase(orderTestCase);
		
		saveSampleTestToFile();
		
	}
	
	private void saveSampleTestToFile() throws IOException {
		
		FileWriter writer = new FileWriter(testFileFinder.getOrderSuiteTestSampleFile());
		JsonUtil.gson.toJson(suite, writer);
		writer.close();
		
	}
	
	
}
