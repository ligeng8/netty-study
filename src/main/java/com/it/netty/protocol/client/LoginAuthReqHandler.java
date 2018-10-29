package com.it.netty.protocol.client;

import com.it.netty.protocol.common.Header;
import com.it.netty.protocol.common.MessageType;
import com.it.netty.protocol.common.NettyMessage;
import com.it.netty.protocol.common.ProtoColException;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class LoginAuthReqHandler extends ChannelHandlerAdapter {

	
	private NettyMessage buildLoginReq() {
		NettyMessage nettyMessage = new NettyMessage();
		Header header = new Header();
		header.setType(MessageType.shakeReq.getValue());
		nettyMessage.setHeader(header);
		return nettyMessage;
	}
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		if(!(msg instanceof NettyMessage)) {
			throw new  ProtoColException("消息体类型不支持");
		}
		NettyMessage message = (com.it.netty.protocol.common.NettyMessage) msg;
		
		if(message.getHeader() != null &&message.getHeader().getType() == MessageType.shakeResp.getValue()) {
			int loginResult = (int) message.getBody();
			if(loginResult != 0) {
			//握手失败，关闭连接
				ctx.close();
			}else {
				System.out.println("Login is ok :"+ message);
				ctx.fireChannelRead(msg);
			}
		}else {
			ctx.fireChannelRead(msg);
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		ctx.writeAndFlush( buildLoginReq());
	}
	
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		ctx.close();
		cause.printStackTrace();
	}
}
