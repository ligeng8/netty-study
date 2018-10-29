package com.it.netty.protocol.server;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.it.netty.protocol.common.Header;
import com.it.netty.protocol.common.MessageType;
import com.it.netty.protocol.common.NettyMessage;
import com.it.netty.protocol.common.ProtoColException;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class LoginAuthRespHandler extends ChannelHandlerAdapter {
	private Map<String, Boolean> nodeCheck = new ConcurrentHashMap<>();

	private String[] whiteList =  {"127.0.0.1","192.168.130.21"};
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if(!(msg instanceof NettyMessage)) {
			throw new  ProtoColException("消息体类型不支持");
		}
		NettyMessage message = (com.it.netty.protocol.common.NettyMessage) msg;
		if(message.getHeader()!= null && message.getHeader().getType() == MessageType.shakeReq.getValue()) {
			String ip = ctx.channel().remoteAddress().toString();
			NettyMessage loginResponse = null;
			if(nodeCheck.containsKey(ip)) {
				loginResponse  = buildResponse(-1);
			}else {
				InetSocketAddress remoteAddress = (InetSocketAddress) ctx.channel().remoteAddress();
				String nip = remoteAddress.getAddress().getHostAddress();
				boolean isok = false;
				for (String wIp : whiteList) {
					if(wIp.equals(nip)) {
						isok = true;
						break ;
					}
				}
				loginResponse = isok ? buildResponse(0) : buildResponse(-1);
				if(isok) {
					nodeCheck.put(ip, true);
				}
				ctx.writeAndFlush(loginResponse);
			}
		}else {
			ctx.fireChannelRead(msg);
		}
	}

	private NettyMessage buildResponse(Object  result) {
		NettyMessage nettyMessage = new NettyMessage();
		Header header = new Header();
		header.setType(MessageType.shakeResp.getValue());
		nettyMessage.setHeader(header);
		nettyMessage.setBody(result);
		return nettyMessage;
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelReadComplete(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		nodeCheck.remove(ctx.channel().remoteAddress().toString());
		ctx.close();
		ctx.fireExceptionCaught(cause);
	}
}
