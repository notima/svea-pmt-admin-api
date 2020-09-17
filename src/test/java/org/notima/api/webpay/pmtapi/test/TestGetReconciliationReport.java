package org.notima.api.webpay.pmtapi.test;

import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class TestGetReconciliationReport extends TestBase {
	
	@Test
	public void testGetReconciliationReport() {
		
		try {
			
			if (!client.isValid()) {
				log.warn("Client not initialized. Skipping test");
				return;
			}

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1);
			Date yesterDay = cal.getTime();
			
			String result = client.getReconciliationReport(yesterDay, true);
			
			System.out.println(result);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			fail(e.getMessage());
			
		}
		
	}
		



}
