package com.it.netty.protocol.common;

import java.io.Serializable;

public class NettyMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6460844725428615880L;

	private Header header;
	
	private Object body;

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	
}
