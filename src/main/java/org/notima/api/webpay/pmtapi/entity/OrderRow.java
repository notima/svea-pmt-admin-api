package org.notima.api.webpay.pmtapi.entity;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class OrderRow {

	@SerializedName("OrderRowId")
	private Long	orderRowId;
	@SerializedName("ArticleNumber")
	private String	articleNumber;
	@SerializedName("Name")
	private String	name;
	@SerializedName("Quantity")
	private Double	quantity;
	@SerializedName("UnitPrice")
	private Double	unitPrice;
	@SerializedName("DiscountPercent")
	private Double	discountPercent;
	@SerializedName("VatPercent")
	private Double	vatPercent;
	@SerializedName("Unit")
	private String	unit;
	@SerializedName("IsCancelled")
	private boolean	isCancelled;
	@SerializedName("Actions")
	private List<String>	actions;
	
	public Long getOrderRowId() {
		return orderRowId;
	}
	public void setOrderRowId(Long orderRowId) {
		this.orderRowId = orderRowId;
	}
	public String getArticleNumber() {
		return articleNumber;
	}
	public void setArticleNumber(String articleNumber) {
		this.articleNumber = articleNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Double getDiscountPercent() {
		return discountPercent;
	}
	public void setDiscountPercent(Double discountPercent) {
		this.discountPercent = discountPercent;
	}
	public Double getVatPercent() {
		return vatPercent;
	}
	public void setVatPercent(Double vatPercent) {
		this.vatPercent = vatPercent;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public boolean isCancelled() {
		return isCancelled;
	}
	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}
	
	
	
}
