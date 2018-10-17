package com.it.netty.timetask;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class InitChildChannelhandler extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// TODO Auto-generated method stub
		ch.pipeline().addLast(new LineBasedFrameDecoder(1024))
		             .addLast(new StringDecoder())
		            .addLast(new  TimeServerHandler2());
	}

}
