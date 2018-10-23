package com.it.netty.xuliehua.jbossMarshaling;

import com.it.netty.xuliehua.bean.SubscribeReq;
import com.it.netty.xuliehua.bean.SubscribeResp;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class SubReqClientHandler extends ChannelHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		for (int j = 0; j <1000000; j++) {
			ctx.write(subReq(j));
		}
		ctx.flush();
	}
	
	public SubscribeReq subReq(int i ) {
		SubscribeReq subscribeReq = new SubscribeReq();
		subscribeReq.setUserName("ligeng");
		subscribeReq.setPhoneNumber("18292811398");
		subscribeReq.setAddress("陕西省西安市雁塔区雁塔西路6号");
		subscribeReq.setSubReqID(i);
		subscribeReq.setProductName("Netty 权威指南 JBOss MarShalling");
		return subscribeReq;
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		SubscribeResp subscribeResp = (SubscribeResp) msg;
		System.out.println(subscribeResp);
		ctx.close();
//		ctx.write("successful;");
	}
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	};
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(ctx, cause);
	}
}
