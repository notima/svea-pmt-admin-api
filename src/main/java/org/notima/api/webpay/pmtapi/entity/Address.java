package org.notima.api.webpay.pmtapi.entity;

import com.google.gson.annotations.SerializedName;

public class Address {

	@SerializedName("FullName")
	private String	fullName;
	
	@SerializedName("StreetAddress")
	private	String	streetAddress;
	
	@SerializedName("CoAddress")
	private String	coAddress;
	
	@SerializedName("PostalCode")
	private String	postalCode;
	
	@SerializedName("City")
	private String	city;
	
	@SerializedName("CountryCode")
	private String	countryCode;
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	public String getCoAddress() {
		return coAddress;
	}
	public void setCoAddress(String coAddress) {
		this.coAddress = coAddress;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	
	
}
