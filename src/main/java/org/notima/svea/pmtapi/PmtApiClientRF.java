package org.notima.svea.pmtapi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import okhttp3.ResponseBody;

import org.notima.svea.pmtapi.entity.Order;
import org.notima.svea.pmtapi.entity.OrderRow;
import org.notima.svea.pmtapi.util.JsonUtil;

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

	public static Logger clientLog = java.util.logging.Logger.getLogger("PmtApiClientRF");

	public static DateFormat dfmt = new SimpleDateFormat("yyyy-MM-dd");
	
	private String merchantId;
	private String secretWord;
	private String serverName;
	
	private	Retrofit	retroFit = null;
	private PmtApiService service = null;
	
	/**
	 * Initializes client from supplied parameters
	 * 
	 * @param	serverName		The server (ie https://server.com)
	 * @param	merchantId		The merchant ID supplied by Svea Ekonomi.
	 * @param	secretWord		The secret word for the given merchant ID.
	 */
	public void init(String serverName, String merchantId, String secretWord) {

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
	 * 
	 * @param orderId	The checkout order ID.
	 * @return			The order as a java object.
	 * @throws Exception
	 */
	public Order getOrder(Long orderId) throws Exception {

		String ts = PmtApiUtil.getTimestampStr();
		String auth = PmtApiUtil.calculateAuthHeader(merchantId, "", secretWord, ts);
		
		Call<ResponseBody> call = service.getOrder(auth, ts, orderId.toString());
		
		Response<ResponseBody> response = call.execute();
		
		String resultMsg = null; 

		if (response.errorBody()!=null) {
			clientLog.warning(response.errorBody().string());
			resultMsg = response.errorBody().string();
		} else {
			resultMsg = response.body().string();
			clientLog.fine(response.message());
			clientLog.fine(resultMsg);
			clientLog.fine(response.raw().toString());
		}		

		if (resultMsg!=null && resultMsg.trim().length()>0) {
			return PmtApiUtil.gson.fromJson(resultMsg, Order.class);
		} else {
			return null;
		}
	}
	
	/**
	 * Tells Svea Ekonomi that this order is delivered and should be billed.
	 * 
	 * @param orderId		The order to be delivered
	 * @return				
	 * @throws Exception
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
			clientLog.warning(response.errorBody().string());
			resultMsg = response.errorBody().string();
		} else {
			if (response.code()==200) {
				resultMsg = response.body().string();
				clientLog.fine(response.message());
				clientLog.fine(resultMsg);
				clientLog.fine(response.raw().toString());
			} else {
				resultMsg = response.message();
				clientLog.warning(response.code() + " : " + response.message());
			}
		}		

		if (resultMsg!=null && resultMsg.trim().length()>0) {
			return resultMsg;
		} else {
			return null;
		}
		
	}
	
	
}
