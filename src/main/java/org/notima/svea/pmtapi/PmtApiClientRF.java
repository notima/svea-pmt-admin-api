package org.notima.svea.pmtapi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.notima.svea.pmtapi.entity.Order;
import org.notima.svea.pmtapi.entity.OrderRow;
import org.notima.svea.pmtapi.entity.OrderRowIdArray;
import org.notima.svea.pmtapi.util.JsonUtil;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

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
	
	private	RestAdapter	retroFit = null;
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
		
		retroFit = new RestAdapter.Builder()
						.setEndpoint(this.serverName)
						.setConverter(new GsonConverter(PmtApiUtil.gson))
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
		
		Order order = null;
		
		try {
			order = service.getOrder(auth, ts, orderId.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return order;
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
		
		OrderRowIdArray body = new OrderRowIdArray();
		body.setOrderRowIds(lines);
		
		String auth = PmtApiUtil.calculateAuthHeader(merchantId, JsonUtil.gson.toJson(body), secretWord, ts);

		String resultMsg = service.deliverOrder(auth, ts, orderId.toString(), body);
		
		return resultMsg;
	}
	
	/**
	 * Tells Svea Ekonomi that this order is delivered and should be billed.
	 * No order rows are supplied, meaning all deliverable rows should be delivered.
	 * 
	 * @param orderId		The order to be delivered
	 * @return				
	 * @throws Exception
	 */
	public String deliverCompleteOrderNoCheck(Long orderId) throws Exception {
		
		
		String ts = PmtApiUtil.getTimestampStr();
		List<Long> lines = new ArrayList<Long>();
		
		OrderRowIdArray body = new OrderRowIdArray(); // Empty array
		body.setOrderRowIds(lines);
		
		String auth = PmtApiUtil.calculateAuthHeader(merchantId, JsonUtil.gson.toJson(body), secretWord, ts);
		
		String resultMsg = service.deliverOrder(auth, ts, orderId.toString(), body);
		
		return resultMsg;
	}
	
	
	
}
