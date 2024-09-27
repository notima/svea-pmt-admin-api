package org.notima.api.webpay.pmtapi;

/**
 * 
 * Copyright 2019 Notima System Integration AB (Sweden)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @author Daniel Tamm
 *
 */


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.TimeZone;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.Strictness;

/**
 * Utility class for talking to Payment API. 
 * 
 * @author Daniel Tamm
 *
 */
public class PmtApiUtil {
	
	// DateTime Format used to communicate with Svea's API (UTC/GMT)
	// This format is only used in the API-communication when setting the timestamp included 
	// in the MAC calculation.
	public static DateFormat dateTimeFmtGMT;
	// Date Format for JSon etc. 
	public static final String dfmtStr = "yyyy-MM-dd";
	public static final DateFormat dateFmt;
	
	// Date Format for Json DateTime
	public static final String dateTimeFmtStr = "yyyy-MM-dd'T'HH:mm:ss";
	public static final DateFormat dateTimeFmt;
	
	
	public static Gson gson;
	
	static {
		
		dateTimeFmtGMT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		dateTimeFmtGMT.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		GsonBuilder builder = new GsonBuilder().setPrettyPrinting().setDateFormat(dfmtStr);
		builder.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY);
		builder.setStrictness(Strictness.LENIENT);
		gson = builder.create();
		
		dateFmt = new SimpleDateFormat(dfmtStr);
		dateTimeFmt = new SimpleDateFormat(dateTimeFmtStr);
		
	}
	
	/**
	 * Calculates a MAC (Message Authentication Key) for given message and secret word.
	 * 
	 * @param merchantId	Merchant ID
	 * @param message		This is the request body (if any)
	 * @param secretWord	This is a presumably base64 encoded secret word that's given
	 * 						to you by Svea Ekonomi.
	 * @param timestamp		Timestamp as a string in format yyyy-MM-dd HH:mm:ss. Timezone must be UTC / GMT.
	 * @return				An auth header.
	 * @throws UnsupportedEncodingException		If something goes wrong
	 */
	public static String calculateAuthHeader(String merchantId, String message, String secretWord, String timestamp) throws UnsupportedEncodingException {

		
		String combined = message + secretWord + timestamp;
		String result = null;
	    try {
	         MessageDigest md = MessageDigest.getInstance("SHA-512");
	         byte[] bytes = md.digest(combined.getBytes("UTF-8"));
	         StringBuilder sb = new StringBuilder();
	         for(int i=0; i< bytes.length ;i++){
	            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	         }
	         result = merchantId + ":" + sb.toString();
	         return "Svea " + base64encodeMsg(result);
	        } 
	       catch (NoSuchAlgorithmException e){
	        e.printStackTrace();
	       }
	    return result;
		
	}

	/**
	 * Returns timestamp string using current time and UTC-timezone.
	 * 
	 * @return		A timestamp formatted as a string.
	 */
	public static String getTimestampStr() {
		// Get time in UTC
		Calendar cal = Calendar.getInstance();
		String ts = PmtApiUtil.dateTimeFmtGMT.format(cal.getTime());
		return ts;
	}
	

	/**
	 * Encodes given message to Base64
	 * 
	 * @param message		An XML-message (plain text) to be encoded.
	 * @return				A base64 encoded message.
	 */
	
	public static String base64encodeMsg(String message) {
		return Base64.getEncoder().encodeToString(message.getBytes());
	}
	
	
	/**
	 * Decodes a base64 message. 
	 * 
	 * @param message		The base64 message.
	 * @return		The message decoded
	 */
	public static String base64decodeMsg(String message) {
		
		StringBuffer buf = new StringBuffer();
		// Strip message from spaces, newlines etc
		char c;
		for (int i = 0; i<message.length(); i++) {
			c = message.charAt(i);
			switch(c) {
				case ' ' :
				case '\n' :
				case '\r' :
				case '\t' :
					continue;
				default:
					buf.append(c);
			}
		}
		String result = new String(Base64.getDecoder().decode(buf.toString())); 
		
		return result;
		
	}
	
	
}
