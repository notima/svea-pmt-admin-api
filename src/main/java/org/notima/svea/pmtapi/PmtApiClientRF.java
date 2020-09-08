package org.notima.svea.pmtapi;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.notima.svea.pmtapi.entity.Delivery;
import org.notima.svea.pmtapi.entity.Order;
import org.notima.svea.pmtapi.entity.OrderRow;
import org.notima.svea.pmtapi.entity.OrderRowIdArray;
import org.notima.svea.pmtapi.util.JsonUtil;

import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedByteArray;

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

		// Allow patch HTTP-call.
		allowMethods("PATCH");
		
		// Disable SNI to prevent SSL-name problem
		// System.setProperty("jsse.enableSNIExtension", "false");
		
		retroFit = new RestAdapter.Builder()
						.setEndpoint(this.serverName)
						.setConverter(new GsonConverter(PmtApiUtil.gson))
						.build();
		
		service = retroFit.create(PmtApiService.class);
		
	}
	
	/**
	 * Allow PATCH method in JDK 1.6
	 * 
	 * See https://stackoverflow.com/questions/25163131/httpurlconnection-invalid-http-method-patch#32503192
	 * 
	 * @param methods
	 */
	private static void allowMethods(String... methods) {
        try {
            Field methodsField = HttpURLConnection.class.getDeclaredField("methods");

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

            methodsField.setAccessible(true);

            String[] oldMethods = (String[]) methodsField.get(null);
            Set<String> methodsSet = new LinkedHashSet<String>(Arrays.asList(oldMethods));
            methodsSet.addAll(Arrays.asList(methods));
            String[] newMethods = methodsSet.toArray(new String[0]);

            methodsField.set(null/*static field*/, newMethods);
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException(e);
        } catch ( IllegalAccessException e) {
        	throw new IllegalStateException(e);
        }
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
	 * Cancels a specific amount on given order.
	 * 
	 * @param orderId
	 * @param cancelledAmount	In normal currency (not minor currency)
	 * @return
	 * @throws Exception
	 */
	public String cancelOrderAmount(Long orderId, double cancelledAmount) throws Exception {

		Long cancelledAmt = Math.round(cancelledAmount * 100);
		Order order = new Order();
		order.setCancelledAmount(cancelledAmt);
		
		return patchOrder(orderId, order);
		
	}
	
	/**
	 * Cancels a complete order.
	 * 
	 * 
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	public String cancelCompleteOrder(Long orderId) throws Exception {
		
		Order order = new Order();
		order.setCancelled(true);
		
		return patchOrder(orderId, order);
		
	}

	/**
	 * Sends a cancel order patch with payload parameters from order
	 * 
	 * @param orderId
	 * @param order
	 * @return
	 * @throws Exception
	 */
	private String patchOrder(Long orderId, Order order) throws Exception {

		String ts = PmtApiUtil.getTimestampStr();
		String auth = PmtApiUtil.calculateAuthHeader(merchantId, JsonUtil.gson.toJson(order), secretWord, ts);
		
		Response response = service.patchOrder(auth, ts, orderId.toString(), order);
		
		String resultMsg = null;
		if (response.getStatus()==204) {
			resultMsg = "OK";
		} else {
			resultMsg = new String(((TypedByteArray) response.getBody()).getBytes());
			if (resultMsg.length()==0)
				resultMsg = Integer.toString(response.getStatus());
		}
		
		return "";
		
	}

	/**
	 * Credits amount on specific delivery
	 * 
	 * @param orderId
	 * @param deliveryId
	 * @param creditedAmount		Amount in normal amount (not minor currency)
	 * @return
	 * @throws Exception
	 */
	public String creditAmount(Long orderId, Long deliveryId, double creditedAmount) throws Exception {
		
		Long creditedAmt = Math.round(creditedAmount * 100);
		
		Delivery delivery = new Delivery();
		delivery.setCreditedAmount(creditedAmt);
		
		return patchDelivery(orderId, deliveryId, delivery);
		
	}
	
	/**
	 * Patch delivery with contents of delivery.
	 * 
	 * @param orderId
	 * @param deliveryId
	 * @param delivery
	 * @return
	 * @throws Exception
	 */
	private String patchDelivery(Long orderId, Long deliveryId, Delivery delivery) throws Exception {

		String ts = PmtApiUtil.getTimestampStr();
		String auth = PmtApiUtil.calculateAuthHeader(merchantId, JsonUtil.gson.toJson(delivery), secretWord, ts);
		
		Response response = service.patchDelivery(auth, 
												  ts, 
												  orderId.toString(), 
												  deliveryId.toString(), 
												  delivery);
		
		String resultMsg = null;
		if (response.getStatus()==204) {
			resultMsg = "OK";
		} else {
			resultMsg = new String(((TypedByteArray) response.getBody()).getBytes());
			if (resultMsg.length()==0)
				resultMsg = Integer.toString(response.getStatus());
		}
		
		return resultMsg;
		
	}
	
	
	/**
	 * Tells Svea Ekonomi that this order is delivered and should be billed.
	 * A call here delivers the complete order.
	 * 
	 * @param orderId		The order to be delivered
	 * @return				
	 * @throws Exception
	 */
	public String deliverCompleteOrder(Long orderId) throws Exception {
		return deliverOrder(orderId, null);
	}
	
	/**
	 * Tells Svea Ekonomi that this order is delivered and should be billed.
	 * 
	 * @param orderId		The order to be delivered
	 * @param deliverRows	The order rows to be delivered. If null, all rows are delivered.
	 * 						The information to be supplied in the orderrows are either
	 * 	                    - OrderLineID - Matching LineIds are delivered
	 *                      or
	 *                      - articleNumber, quantity and unitPrice - Matching rows are delivered.
	 *                       Rows without article number are matched on quantity and unitPrice.
	 *                       If row has articleNumber but not qty or unitPrice it matches all with same articleNumber
	 * 						@see deliverCompleteOrderNoCheck(Long)
	 * @return				
	 * @throws Exception
	 */
	public String deliverOrder(Long orderId, List<OrderRow> deliverRows) throws Exception {
		
		if (deliverRows==null)
			return deliverCompleteOrderNoCheck(orderId);
		
		Order order = getOrder(orderId);
		if (order==null) return("Can't deliver order " + orderId + " (not found)");
		
		if ("Delivered".equals(order.getOrderStatus())) {
			return orderId + " already delivered";
		}
		
		String ts = PmtApiUtil.getTimestampStr();
		List<Long> lines = new ArrayList<Long>();
		
		// Read order rows from Svea
		List<OrderRow> rowList = order.getOrderRows();
		
		// Build map based on OrderRowId
		Map<Long,OrderRow> rowIdMap = new TreeMap<Long,OrderRow>();
		// and build map based on article Id
		Map<String, List<OrderRow>> artNoMap = new TreeMap<String,List<OrderRow>>();
		List<OrderRow> orList = null;
		String artNo = null;
		for (OrderRow or : deliverRows) {
			if (or.getOrderRowId()!=null && or.getOrderRowId()>0L) {
				rowIdMap.put(or.getOrderRowId(), or);
			}
			if (or.getArticleNumber()!=null && or.getArticleNumber().trim().length()>0) {
				artNo = or.getArticleNumber();
			} else {
				// No Article ID
				artNo = "";
			}
			orList = artNoMap.get(artNo);
			if (orList==null) {
				orList = new ArrayList<OrderRow>();
				artNoMap.put(artNo, orList);
			}
			orList.add(or);
		}
		
		for (OrderRow or : rowList) {
			// Exclude cancelled order lines
			if (or.isCancelled())
				continue;
			// Check if this line should be delivered
			
			// If the row ID is specified in deliverRows, we add the line.
			if (rowIdMap.get(or.getOrderRowId())!=null) {
				lines.add(or.getOrderRowId());
				continue;
			}
			
			// If the article is specified in deliverRows, we add the line if quantity and price match 
			// if price and qty is set on the deliverRow.
			artNo = or.getArticleNumber()!=null ? or.getArticleNumber() : "";
			orList = artNoMap.get(artNo);
			if (orList!=null) {
				for (OrderRow deliverRow : orList) {
					if ((deliverRow.getQuantity()==null || (deliverRow.getQuantity().equals(or.getQuantity()))) && 
							(deliverRow.getUnitPrice()==null || deliverRow.getUnitPrice().equals(or.getUnitPrice()))) {
						lines.add(or.getOrderRowId());
					}
				}
			}
		}
		
		if (lines.size()==0) {
			return ("No lines to deliver on checkout order " + orderId);
		}
		
		OrderRowIdArray body = new OrderRowIdArray();
		body.setOrderRowIds(lines);
		
		String auth = PmtApiUtil.calculateAuthHeader(merchantId, JsonUtil.gson.toJson(body), secretWord, ts);
				
		Response response = service.deliverOrder(auth, ts, orderId.toString(), body);
		
		String resultMsg = null;
		if (response.getBody()==null) {
			resultMsg = "OK";
		} else {
			resultMsg = new String(((TypedByteArray) response.getBody()).getBytes());
			if (resultMsg.length()==0)
				resultMsg = "OK";
		}
		
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
		
		Response response = service.deliverOrder(auth, ts, orderId.toString(), body);
		
		String resultMsg = null;
		if (response.getStatus()==202) {
			resultMsg = "OK";
		} else {
			resultMsg = new String(((TypedByteArray) response.getBody()).getBytes());
			if (resultMsg.length()==0)
				resultMsg = "OK";
		}
		
		return resultMsg;
	}
	
	
	
}
