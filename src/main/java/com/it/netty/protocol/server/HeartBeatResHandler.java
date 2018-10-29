package com.it.netty.protocol.server;

import com.it.netty.protocol.common.Header;
import com.it.netty.protocol.common.MessageType;
import com.it.netty.protocol.common.NettyMessage;
import com.it.netty.protocol.common.ProtoColException;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class HeartBeatResHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if(!(msg instanceof NettyMessage)) {
			throw new  ProtoColException("消息体类型不支持");
		}
		NettyMessage message = (com.it.netty.protocol.common.NettyMessage) msg;
		if(message.getHeader() != null && message.getHeader().getType() == MessageType.HEARTReq.getValue() ) {
			NettyMessage nettyMessage = new NettyMessage();
			Header header = new Header();
			header.setType(MessageType.HEARTResp.getValue());
			nettyMessage.setHeader(header);
			ctx.writeAndFlush(nettyMessage);
		}else {
			ctx.fireChannelRead(msg);
		}
	}
}
