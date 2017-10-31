package se.sveaekonomi.webpay.pmtapi;

import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.slf4j.Logger;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import se.sveaekonomi.webpay.pmtapi.entity.Order;
import se.sveaekonomi.webpay.pmtapi.util.JsonUtil;

public class PmtApiClientRF {

	public static Logger clientLog = org.slf4j.LoggerFactory.getLogger("PmtApiClientRF");

	public static DateFormat dfmt = new SimpleDateFormat("yyyy-MM-dd");
	
	private String merchantId;
	private String secretWord;
	private String serverName;
	
	
	private Configurations configs = new Configurations();
	
	private	Retrofit	retroFit = null;
	private PmtApiService service = null;

	
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
			System.out.println("Can't find configfile: " + configfile);
			System.exit(-1);
		}
		
		XMLConfiguration fc = configs.xml(url);
		
		serverName = fc.getString("server");
		merchantId = fc.getString("merchantId");
		secretWord = fc.getString("secretWord");
		
	}	
	
	/**
	 * Initializes client with current values. loadConfig must have been called before.
	 * 
	 */
	public void init() {
		init(serverName, merchantId, secretWord);
	}
	
	/**
	 * Initializes client
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
			return PmtApiUtil.gson.fromJson(resultMsg, Order.class);
		} else {
			return null;
		}
	}
	
	
	public String deliverCompleteOrder(Long orderId) throws Exception {
		
		String ts = PmtApiUtil.getTimestampStr();
		List<Long> lines = new ArrayList<Long>();
		lines.add(1L);
		lines.add(2L);
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
	
	
}
