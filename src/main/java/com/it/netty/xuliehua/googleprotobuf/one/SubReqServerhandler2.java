package com.it.netty.xuliehua.googleprotobuf.one;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import com.it.netty.xuliehua.bean.SubscribeReq;
import com.it.netty.xuliehua.bean.SubscribeResp;

import io.netty.channel.ChannelHandler.Sharable;
@Sharable
public class SubReqServerhandler2 extends ChannelHandlerAdapter {

	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		 SubscribeReq req = (SubscribeReq) msg;
		  if("ligeng".equals(req.getUserName())) {
			  System.out.println("Service accept client subscribe req:"+ req);
			  ctx.write(resp( req));
		  }
	}
	
	public SubscribeResp resp(SubscribeReq req){
		
		SubscribeResp subscribeResp2 = new SubscribeResp();
		subscribeResp2.setSubReqID(req.getSubReqID());
		subscribeResp2.setRespCode(0);
		subscribeResp2.setDesc("Netty book order successd ,3 days later ,sent to "+ req.getAddress());
		return subscribeResp2;
		
	}
	
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		ctx.flush();
	}
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelActive(ctx);
	}
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelInactive(ctx);
	}
	

	
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
	}
	
}
