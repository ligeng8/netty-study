package com.it.netty.xuliehua.bean;

import java.io.Serializable;

public class SubscribeResp implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 357874242289248413L;

	
	private Integer subReqID;
	
	private Integer respCode;
	
	private String desc;

	public Integer getSubReqID() {
		return subReqID;
	}

	public void setSubReqID(Integer subReqID) {
		this.subReqID = subReqID;
	}

	public Integer getRespCode() {
		return respCode;
	}

	public void setRespCode(Integer respCode) {
		this.respCode = respCode;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "SubscribeResp [subReqID=" + subReqID + ", respCode=" + respCode + ", desc=" + desc + "]";
	}

	public SubscribeResp(Integer subReqID, Integer respCode, String desc) {
		super();
		this.subReqID = subReqID;
		this.respCode = respCode;
		this.desc = desc;
	}

	public SubscribeResp() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
