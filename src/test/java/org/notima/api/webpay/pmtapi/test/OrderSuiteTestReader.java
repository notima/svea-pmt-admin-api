package org.notima.api.webpay.pmtapi.test;

import java.io.FileReader;
import java.io.IOException;

import org.notima.api.webpay.pmtapi.util.JsonUtil;

public class OrderSuiteTestReader {

	private TestFileFinder fileFinder;
	private OrderTestSuite	testSuite;
	
	public static OrderSuiteTestReader buildFromFileFinder(TestFileFinder fileFinder) {
		
		OrderSuiteTestReader ostr = new OrderSuiteTestReader();
		ostr.fileFinder = fileFinder;
		return ostr;
		
	}

	public void loadSampleTestsFromFile() throws IOException {
		
		loadSampleTestsFromFile(fileFinder.getOrderSuiteTestSampleFile());
		
	}

	public void loadCustomTestsFromFile() throws IOException {
		
		loadSampleTestsFromFile(fileFinder.getOrderSuiteTestCustomFile());
		
	}

	public OrderTestSuite getOrderTestSuite() {
		return testSuite;
	}
	
	private void loadSampleTestsFromFile(String filename) throws IOException {

		FileReader reader = new FileReader(filename);
		testSuite = JsonUtil.gson.fromJson(reader, OrderTestSuite.class);
		reader.close();
		
	}
	
}
