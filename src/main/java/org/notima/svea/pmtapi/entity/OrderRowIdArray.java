package org.notima.svea.pmtapi.entity;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class OrderRowIdArray {

	@SerializedName("orderRowIds")
	private List<Long> orderRowIds;

	public List<Long> getOrderRowIds() {
		return orderRowIds;
	}

	public void setOrderRowIds(List<Long> orderRowIds) {
		this.orderRowIds = orderRowIds;
	}
	
}
