package com.it.netty.xuliehua.googleprotobuf;

import java.util.ArrayList;
import java.util.List;

import com.it.netty.xuliehua.googleprotobuf.SubscribeReqProto.SubscribeReq;
import com.it.netty.xuliehua.googleprotobuf.SubscribeReqProto.SubscribeReq.Builder;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class SubReqClientHandler extends ChannelHandlerAdapter {

	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
		for (int i = 0; i < 20; i++) {
			ctx.write(ss( i));
		}
		ctx.flush();
	}
	
	public SubscribeReq ss(int i) {
		Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
		builder.setUserName("ligeng")
		       .setPhoneNumber("18292811398")
		       .setProductName("Netty È¨ÍþÖ¸ÄÏ")
		       .setSubReqID(i);
		List<String> values = new ArrayList<>();
		values.add("Beijing san");
		values.add("Nanjing shifu");
		values.add("hangzhou guozepeng");
		builder.addAllAddress(values);
		
		return builder.build();
		
		
	}
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelInactive(ctx);
	}
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("client receive:"+ msg);
	}
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelReadComplete(ctx);
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(ctx, cause);
	}
	
}
