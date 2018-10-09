package org.notima.svea.pmtapi;

import org.notima.svea.pmtapi.entity.Order;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;

public interface PmtApiService {

	@Headers({
		"Content-type: application/json"
	})
	@GET("/api/v1/orders/{orderId}")
	Order getOrder(
			@Header("Authorization")String authorization,
			@Header("Timestamp")String timestamp,
			@Path("orderId")String orderId);
	

	@Headers({
		"Content-type: application/json"
	})
	@POST("/api/v1/orders/{orderId}/deliveries")
	Response deliverOrder(
			@Header("Authorization")String auth,
			@Header("Timestamp")String ts,
			@Path("orderId")String orderId,
			@Body()Object jsonListOfLineIds);
	
}
