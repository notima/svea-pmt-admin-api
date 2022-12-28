package org.notima.api.webpay.pmtapi.test;

import static org.junit.Assert.fail;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class TestGetReconciliationReport extends TestBase {
	
	DateFormat dfmt = new SimpleDateFormat("yyyy-MM-dd");
	
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
			
			Date specificDate = null;
			
			specificDate = dfmt.parse("2022-12-09");
			
			String result = client.getReconciliationReport(specificDate!=null ? specificDate : yesterDay, true);
			
			System.out.println(result);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			fail(e.getMessage());
			
		}
		
	}
		



}
