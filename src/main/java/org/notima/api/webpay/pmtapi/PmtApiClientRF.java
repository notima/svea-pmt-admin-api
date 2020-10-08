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

import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;

import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.notima.api.webpay.pmtapi.entity.Order;
import org.notima.api.webpay.pmtapi.entity.OrderRow;
import org.notima.api.webpay.pmtapi.util.JsonUtil;
import org.slf4j.Logger;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * This is a RetroFit-based REST-client to Svea Ekonomi's Payment Admin API.
 * 
 * The client must be initialized with 
 * * Merchant ID
 * * Secret Word
 * * Server name
 * 
 * @author Daniel Tamm
 *
 */
public class PmtApiClientRF {

	public static Logger clientLog = org.slf4j.LoggerFactory.getLogger("PmtApiClientRF");

	public static DateFormat dfmt = new SimpleDateFormat("yyyy-MM-dd");
	
	public static final String DEFAULT_SERVERNAME = "https://paymentadminapi.svea.com";
	
	private String merchantId;
	private String secretWord;
	private String serverName;
	
	
	private Configurations configs = new Configurations();
	
	private	Retrofit	retroFit = null;
	private PmtApiService service = null;

	/**
	 * Loads configuration from a configuration file. The file must be available as a resource
	 * or the full path should be supplied.
	 * 
	 * @param configfile		The name (if resource) or full path + name of the configuration file.
	 * @throws Exception		If something goes wrong
	 */
	public void loadConfig(String configfile) throws Exception {

		URL url = null;
		
		// Try absolute path first
		File cf = new File(configfile);
		if (!cf.exists()) {
			// Try read as resource
			url = ClassLoader.getSystemResource(configfile);
		} else {
			url = new URL(cf.getAbsolutePath());
		}

		if (url==null) {
			clientLog.error("Can't find configfile: {}", configfile);
			return;
		}
		
		XMLConfiguration fc = configs.xml(url);
		
		serverName = fc.getString("server");
		merchantId = fc.getString("merchantId");
		secretWord = fc.getString("secretWord");
		
		if (serverName==null) serverName = DEFAULT_SERVERNAME;
		
	}	
	
	/**
	 * Initializes client with current values. loadConfig must have been called before.
	 * 
	 */
	public void init() {
		init(serverName, merchantId, secretWord);
	}
	
	/**
	 * Initializes client from supplied parameters
	 * 
	 * @param	serverName		The server (ie https://server.com)
	 * @param	merchantId		The merchant ID supplied by Svea Ekonomi.
	 * @param	secretWord		The secret word for the given merchant ID.
	 */
	public void init(String serverName, String merchantId, String secretWord) {

		if (serverName==null) serverName = DEFAULT_SERVERNAME;		
		
		this.serverName = serverName;
		this.merchantId = merchantId;
		this.secretWord = secretWord;
		
		// Disable SNI to prevent SSL-name problem
		// System.setProperty("jsse.enableSNIExtension", "false");
		
		ScalarsConverterFactory converter = ScalarsConverterFactory.create();
		
		retroFit = new Retrofit.Builder().baseUrl(this.serverName)
				.addConverterFactory(converter)
				.build();

		service = retroFit.create(PmtApiService.class);		
		
	}
	
	/**
	 * Checks if this client has been initialized with credentials
	 * 
	 * @return	True if there are values in serverName, merchantId and secretWord.
	 */
	public boolean isValid() {
		return (serverName!=null && serverName.trim().length()>0 &&
				merchantId!=null && merchantId.trim().length()>0 && 
				secretWord!=null && secretWord.trim().length()>0);
	}
	
	/**
	 * 
	 * @param orderId	The checkout order ID.
	 * @return			The order as a java object.
	 * @throws Exception	If something goes wrong
	 */
	public Order getOrder(Long orderId) throws Exception {

		String ts = PmtApiUtil.getTimestampStr();
		String auth = PmtApiUtil.calculateAuthHeader(merchantId, "", secretWord, ts);
		
		Call<ResponseBody> call = service.getOrder(auth, ts, orderId.toString());
		
		Response<ResponseBody> response = call.execute();
		
		String resultMsg = null; 

		if (response.errorBody()!=null) {
			clientLog.debug(response.errorBody().string());
			resultMsg = response.errorBody().string();
		} else {
			resultMsg = response.body().string();
			clientLog.debug(response.message());
			clientLog.debug(resultMsg);
			clientLog.debug(response.raw().toString());
		}		

		if (resultMsg!=null && resultMsg.trim().length()>0) {
			Order result = PmtApiUtil.gson.fromJson(resultMsg, Order.class);
			return result;
		} else {
			return null;
		}
	}
	
	/**
	 * Tells Svea Ekonomi that this order is delivered and should be billed.
	 * 
	 * @param orderId		The order to be delivered
	 * @return				OK if the order is successfully delivered.				
	 * @throws Exception	If something goes wrong
	 */
	public String deliverCompleteOrder(Long orderId) throws Exception {
		
		Order order = getOrder(orderId);
		if (order==null) return("Can't deliver order " + orderId + " (not found)");
		
		if ("Delivered".equals(order.getOrderStatus())) {
			return orderId + " already delivered";
		}
		
		String ts = PmtApiUtil.getTimestampStr();
		List<Long> lines = new ArrayList<Long>();
		
		List<OrderRow> rowList = order.getOrderRows();
		for (OrderRow or : rowList) {
			if (!or.isCancelled()) {
				lines.add(or.getOrderRowId());
			}
		}
		
		String body = "{ \"orderRowIds\": " + JsonUtil.gson.toJson(lines) + " }";
		
		String auth = PmtApiUtil.calculateAuthHeader(merchantId, body, secretWord, ts);

		Call<ResponseBody> call = service.deliverOrder(auth, ts, orderId.toString(), body);
		
		Response<ResponseBody> response = call.execute();
		
		String resultMsg = null; 

		if (response.errorBody()!=null && response.errorBody().string()!=null && response.errorBody().string().length()>0) {
			clientLog.debug(response.errorBody().string());
			resultMsg = response.errorBody().string();
		} else {
			if (response.code()==200) {
				resultMsg = response.body().string();
				clientLog.debug(response.message());
				clientLog.debug(resultMsg);
				clientLog.debug(response.raw().toString());
			} else {
				resultMsg = response.message();
				clientLog.debug(response.code() + " : " + response.message());
			}
		}		

		if (resultMsg!=null && resultMsg.trim().length()>0) {
			return resultMsg;
		} else {
			return null;
		}
		
	}
	

	/**
	 * 
	 * @param reportDate			The date for the report.
	 * @param includeWithholding	If withholding information should be included.
	 * @return						The report in Json. If there's an error, the raw error message is returned.
	 * @throws Exception	If something goes wrong
	 */
	public String getReconciliationReport(Date reportDate, boolean includeWithholding) throws Exception {

		String ts = PmtApiUtil.getTimestampStr();
		String auth = PmtApiUtil.calculateAuthHeader(merchantId, "", secretWord, ts);

		String dateStr = dfmt.format(reportDate);
		
		Call<ResponseBody> call = service.getReconciliationReport(auth, ts, dateStr, includeWithholding);
		
		Response<ResponseBody> response = call.execute();
		
		String resultMsg = null; 

		if (response.errorBody()!=null) {
			clientLog.warn(response.errorBody().string());
			clientLog.warn(response.toString());
			resultMsg = response.toString();
		} else {
			resultMsg = response.body().string();
			clientLog.debug(response.message());
			clientLog.debug(resultMsg);
			clientLog.debug(response.raw().toString());
		}		

		if (resultMsg!=null && resultMsg.trim().length()>0) {
			return resultMsg;
		} else {
			return null;
		}
	}
	
	
}
