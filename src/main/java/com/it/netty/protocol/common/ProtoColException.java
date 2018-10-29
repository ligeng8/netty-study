package com.it.netty.protocol.common;

public class ProtoColException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7649337544328409998L;

	public ProtoColException() {
		// TODO Auto-generated constructor stub
	}

	public ProtoColException(String message) {
		super(message);
	}

	public ProtoColException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProtoColException(Throwable cause) {
		super(cause);
	}

	protected ProtoColException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
