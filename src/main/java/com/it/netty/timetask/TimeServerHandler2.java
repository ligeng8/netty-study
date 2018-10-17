package com.it.netty.timetask;

import java.util.Date;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultChannelHandlerContext;
import io.netty.util.CharsetUtil;

public class TimeServerHandler2 extends ChannelHandlerAdapter {

	
	private volatile AtomicInteger  a = new AtomicInteger(0);
	public static ConcurrentHashMap<String, ChannelHandlerContext> surChannel = new ConcurrentHashMap<>();
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String req = (String) msg;//去除换行符的内容
		int incrementAndGet = a.incrementAndGet();
		System.out.println("The timeServer receice ORDER :"+req +" :"+ incrementAndGet);
		if("QUERY TIME ORDER".equalsIgnoreCase(req)) {
			String currentTime = new Date().toLocaleString()+ System.getProperty("line.separator") ;
			ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
			ctx.write(resp);
		 }else if(req.startsWith("sur=")){
			 if(ctx instanceof DefaultChannelHandlerContext) {
				 DefaultChannelHandlerContext ss = (DefaultChannelHandlerContext) ctx;
				 ss.setSubr(req);
				 surChannel.put(req, ctx);
				
			 }
		 }
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		 if(ctx instanceof DefaultChannelHandlerContext) {
			 DefaultChannelHandlerContext ss = (DefaultChannelHandlerContext) ctx;
			 surChannel.remove(ss.getSubr());
		 }
		 ctx.close();
	}

	public static void pulishAll() {
		Set<Entry<String, ChannelHandlerContext>> entrySet = surChannel.entrySet();
		for (Entry<String, ChannelHandlerContext> entry : entrySet) {
			ChannelHandlerContext chc = entry.getValue();
			String currentTime ="  sendAll sur" ;
			ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
			chc.writeAndFlush(resp);
		}
		System.out.println("  sendAll sur   end");
	}
}
