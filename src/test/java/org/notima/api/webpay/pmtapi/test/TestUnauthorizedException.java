package org.notima.api.webpay.pmtapi.test;

import org.junit.Test;
import org.notima.api.webpay.pmtapi.exception.UnauthorizedException;

public class TestUnauthorizedException {

	@Test
	public void testUnauthorizedException() {
		
		UnauthorizedException ue = new UnauthorizedException("123456", "abcdefghjijsklrjsldj");
		org.junit.Assert.assertEquals("Incorrect credentials for merchantId 123456 and secret abcde...jsldj", ue.getMessage());
		
		ue = new UnauthorizedException("123456", "abc");

		org.junit.Assert.assertEquals("Incorrect credentials for merchantId 123456 and secret a*****", ue.getMessage());
		
		
	}

}
