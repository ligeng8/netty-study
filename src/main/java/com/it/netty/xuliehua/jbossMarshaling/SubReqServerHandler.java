package com.it.netty.xuliehua.jbossMarshaling;

import com.it.netty.xuliehua.bean.SubscribeReq;
import com.it.netty.xuliehua.bean.SubscribeResp;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class SubReqServerHandler extends ChannelHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelActive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		if (msg != null) {
			if (msg instanceof SubscribeReq) {
				SubscribeReq subscribeReq = (SubscribeReq) msg;
				if (subscribeReq.getUserName().equalsIgnoreCase("ligeng")) {
					System.out.println("server receive rep:" + subscribeReq);
					ctx.writeAndFlush(resp(subscribeReq));
				}
			} else {
				System.out.println(msg);
			}
		}
	}

	public SubscribeResp resp(SubscribeReq subscribeReq) {
		SubscribeResp subscribeResp = new SubscribeResp();
		subscribeResp.setSubReqID(subscribeReq.getSubReqID());
		subscribeResp.setRespCode(0);
		subscribeResp.setDesc("gao wanle ");
		return subscribeResp;

	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelReadComplete(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
	}
}
