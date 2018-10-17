package com.it;

import java.io.Serializable;
import java.nio.ByteBuffer;

public class UserInfo implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1560454611423452615L;

	public static void main(String[] args) {
		
	}
	private String userName;
	
	private Integer userId;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public byte[] codc(ByteBuffer buffer) {
		buffer.clear();
		byte[] bytes = this.userName.getBytes();
		buffer.putInt(bytes.length);
		buffer.put(bytes);
		buffer.putInt(userId);
		buffer.flip();
		byte[] ss =new byte[buffer.remaining()];
		buffer.get(ss);
		return ss;
	}
}
