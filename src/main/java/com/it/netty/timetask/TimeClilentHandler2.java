package com.it.netty.timetask;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

public class TimeClilentHandler2 extends ChannelHandlerAdapter{

	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		for (int i = 0; i < 100; i++) {
			
			byte[] bytes = ("QUERY TIME ORDER"+System.getProperty("line.separator")).getBytes();
			ByteBuf buffer = Unpooled.copiedBuffer(bytes);
			ctx.writeAndFlush(buffer);
		}
//		super.channelActive(ctx);
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelInactive(ctx);
	}
	
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String mesage = (String) msg;
		
		
		System.out.println("client receive :"+ mesage);
		ByteBuf buffer = Unpooled.copiedBuffer("sur=netty".getBytes());
		ctx.write(buffer);
		
	};
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		ctx.close();
//		super.exceptionCaught(ctx, cause);
	}
	}
