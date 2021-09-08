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

	public Double getAmount() {
		return Amount;
	}

	public void setAmount(Double amount) {
		Amount = amount;
	}

	public List<OrderRow> getOrderRows() {
		return orderRows;
	}

	public void setOrderRows(List<OrderRow> orderRows) {
		this.orderRows = orderRows;
	}

	public List<String> getActions() {
		return actions;
	}

	public void setActions(List<String> actions) {
		this.actions = actions;
	}
	
	
	
}
