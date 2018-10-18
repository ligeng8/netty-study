package com.it.netty.xuliehua.googleprotobuf;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import com.it.netty.xuliehua.googleprotobuf.SubscribeReqProto.SubscribeReq;
import com.it.netty.xuliehua.googleprotobuf.SubscribeRespProto.SubscribeResp;
import com.it.netty.xuliehua.googleprotobuf.SubscribeRespProto.SubscribeResp.Builder;

import io.netty.channel.ChannelHandler.Sharable;
@Sharable
public class SubReqServerhandler extends ChannelHandlerAdapter {

	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		  SubscribeReqProto.SubscribeReq req = (SubscribeReq) msg;
		  if("ligeng".equals(req.getUserName())) {
			  System.out.println("Service accept client subscribe req:"+ req);
			  ctx.write(resp( req));
		  }
	}
	
	public SubscribeRespProto.SubscribeResp resp(SubscribeReq req){
		Builder newBuilder = SubscribeRespProto.SubscribeResp.newBuilder();
		SubscribeResp subscribeResp = newBuilder.setSubReqId(req.getSubReqID())
		          .setRespCode(0)
		          .setDesc("Netty book order successd ,3 days later ,sent to "+ req.getAddress(0)).build();
		return subscribeResp;
		
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
