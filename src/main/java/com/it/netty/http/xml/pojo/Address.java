package com.it.netty.http.xml.pojo;

public class Address {
	/**
	 * 街道1
	 */
	private String street1;
	/**
	 * 街道2
	 */
	private String street2;
	/**
	 * 城市
	 */
	private String city;
	/**
	 * 省
	 */
	private String province;

	private String postcode;

	private String nation;

	public String getStreet1() {
		return street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

}
