package com.it.netty.xuliehua.bean;

import java.io.Serializable;

public class SubscribeReq implements Serializable{
	private Integer subReqID;
	private String userName;

	private String productName;

	private String phoneNumber;

	private String address;

	public Integer getSubReqID() {
		return subReqID;
	}

	public void setSubReqID(Integer subReqID) {
		this.subReqID = subReqID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public SubscribeReq(Integer subReqID, String userName, String productName, String phoneNumber, String address) {
		super();
		this.subReqID = subReqID;
		this.userName = userName;
		this.productName = productName;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}

	@Override
	public String toString() {
		return "SubscribeReq [subReqID=" + subReqID + ", userName=" + userName + ", productName=" + productName
				+ ", phoneNumber=" + phoneNumber + ", address=" + address + "]";
	}

	public SubscribeReq() {
		super();
		// TODO Auto-generated constructor stub
	}

}
