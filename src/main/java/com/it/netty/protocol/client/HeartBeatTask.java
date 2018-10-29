package com.it.netty.protocol.client;

import com.it.netty.protocol.common.Header;
import com.it.netty.protocol.common.MessageType;
import com.it.netty.protocol.common.NettyMessage;

import io.netty.channel.ChannelHandlerContext;

public class HeartBeatTask implements Runnable{

	private ChannelHandlerContext ctx;
	
	public HeartBeatTask(ChannelHandlerContext ctx) {
		super();
		this.ctx = ctx;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		NettyMessage nettyMessage = new NettyMessage();
		Header header = new Header();
		header.setType(MessageType.HEARTReq.getValue());
		nettyMessage.setHeader(header);
		ctx.writeAndFlush(nettyMessage);
	}

}
