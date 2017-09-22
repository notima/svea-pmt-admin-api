package se.sveaekonomi.webpay.pmtapi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface PmtApiService {

	@Headers({
		"Content-type: application/json"
	})
	@GET("/api/v1/orders/{orderId}")
	Call<ResponseBody> getOrder(
			@Header("Timestamp")String timestamp,
			@Header("Authorization")String authorization,
			@Path("orderId")String orderId);
	
}
