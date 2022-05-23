package org.notima.api.webpay.pmtapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.notima.api.webpay.pmtapi.exception.InvalidAuthorizationException;
import org.notima.api.webpay.pmtapi.exception.NoSuchOrderException;
import org.notima.api.webpay.pmtapi.exception.NoSuchPmtApiClientException;

public class PmtApiClientCollection {

	private Map<String, PmtApiClientRF>	clients;
	
	/**
	 * Adds or replaces a client based on it's credentials.
	 * 
	 * @param credential
	 */
	public void addPmtApiClient(PmtApiCredential credential) {
		
		if (clients==null) {
			clients = new TreeMap<String, PmtApiClientRF>();
		}
		PmtApiClientRF client = PmtApiClientRF.buildFromCredentials(credential);
		clients.put(client.getIdentifier(), client);
		
	}

	public PmtApiClientRF getPmtApiClient(String identifier) throws NoSuchPmtApiClientException {
		if (clients==null) throw new NoSuchPmtApiClientException(identifier);
		PmtApiClientRF client = clients.get(identifier); 
		if (client==null)
			throw new NoSuchPmtApiClientException(identifier);
		return client;
	}
	
	public List<PmtApiClientRF> getAllPmtApiClients() {
		if (clients==null) return new ArrayList<PmtApiClientRF>();
		
		return (new ArrayList<PmtApiClientRF>(clients.values()));
	}

	/**
	 * Returns a checkout order using a collection of clients.
	 * 
	 * @param orderId
	 * @return
	 * @throws NoSuchOrderException
	 * @throws Exception
	 */
	public CheckoutOrder getCheckoutOrder(Long orderId) throws NoSuchOrderException, Exception {
		
		StringBuffer merchantIds = new StringBuffer();
		CheckoutOrder order = null;
		for (PmtApiClientRF client : clients.values()) {
			try {
				order = client.getCheckoutOrder(orderId);
				order.setMerchantId(client.getIdentifier());
			} catch (NoSuchOrderException nsoe) {
				merchantIds.append(client.getIdentifier() + " ");
			} catch (InvalidAuthorizationException iae) {
				merchantIds.append(client.getIdentifier() + " ");
			}
		}
		if (order==null) {
			NoSuchOrderException nsoe = new NoSuchOrderException(orderId);
			nsoe.setMerchantId(merchantIds.toString());
			throw nsoe;
		}

		return order;
	}
	
}
