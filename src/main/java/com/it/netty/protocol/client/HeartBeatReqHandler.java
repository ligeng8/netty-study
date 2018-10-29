package com.it.netty.protocol.client;

import java.util.concurrent.TimeUnit;

import com.it.netty.protocol.common.MessageType;
import com.it.netty.protocol.common.NettyMessage;
import com.it.netty.protocol.common.ProtoColException;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.ScheduledFuture;
@Sharable
public class HeartBeatReqHandler extends ChannelHandlerAdapter {

	private volatile ScheduledFuture<?> heartBeat = null;
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if(!(msg instanceof NettyMessage)) {
			throw new  ProtoColException("消息体类型不支持");
		}
		NettyMessage message = (com.it.netty.protocol.common.NettyMessage) msg;
		if(message.getHeader() != null && message.getHeader().getType() == MessageType.shakeResp.getValue() ) {
			heartBeat = ctx.executor().scheduleAtFixedRate(new HeartBeatTask(ctx), 0, 5000, TimeUnit.MILLISECONDS);
		}else if(message.getHeader() != null && message.getHeader().getType() == MessageType.HEARTResp.getValue()) {
			System.out.println(message);
		}else {
			ctx.fireChannelRead(msg);
		}
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		if(heartBeat != null) {
			heartBeat.cancel(true);
			heartBeat = null ;
		}
		ctx.fireExceptionCaught(cause);
	}
}
