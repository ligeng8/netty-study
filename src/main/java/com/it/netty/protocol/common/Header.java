package com.it.netty.protocol.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Header implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4718304942138636222L;

	private int crcCode = 0xabef0101;// 版本号

	private int length;// 消息的长度

	private long sessionID;// 会话ID

	private int type; // 消息类型

	private byte priority;// 消息优先级

	private Map<String, Object> attachment = new HashMap<>();// 附件

	public int getCrcCode() {
		return crcCode;
	}

	public void setCrcCode(int crcCode) {
		this.crcCode = crcCode;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public long getSessionID() {
		return sessionID;
	}

	public void setSessionID(long sessionID) {
		this.sessionID = sessionID;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public byte getPriority() {
		return priority;
	}

	public void setPriority(byte priority) {
		this.priority = priority;
	}

	public Map<String, Object> getAttachment() {
		return attachment;
	}

	public void setAttachment(Map<String, Object> attachment) {
		this.attachment = attachment;
	}

}
