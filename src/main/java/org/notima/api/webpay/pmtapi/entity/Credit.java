package org.notima.api.webpay.pmtapi.entity;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Credit {

	@SerializedName("Amount")
	private	Double	Amount;
	
	@SerializedName("OrderRows")
	private List<OrderRow>	orderRows;
	
	@SerializedName("Actions")
	private List<String>	actions;
	
}
