package com.it.netty.echo;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class ChannelHandlerInitial2 extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		
		ch.pipeline().addLast(new FixedLengthFrameDecoder(20))
		             .addLast(new StringDecoder())
		             .addLast(new EchoServerHandler());
		
	}

}
