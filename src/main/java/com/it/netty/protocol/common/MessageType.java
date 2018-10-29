package com.it.netty.protocol.common;

public enum MessageType {

	Req(0, "Req"),
	ResP(1, "ResP"),
	ONEWAY(2, "ONEWAY"),
	shakeReq(3, "shakeReq"),
	shakeResp(4, "shakeResp"),
	HEARTReq(5, "HEARTReq"),
	HEARTResp(6, "HEARTResp")
	;
	private int value;

	private String name;

 MessageType(int value,String name) {
	this.value = value;
	this.name = name;
}

public int getValue() {
	return value;
}

public void setValue(int value) {
	this.value = value;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}
public static void main(String[] args) {
	String name2 = MessageType.HEARTReq.name;
	System.out.println("name:"+ name2);
	int value2 = MessageType.HEARTReq.value;
	System.out.println(value2);
}
}