package com.it.netty.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class ChannelHandlerInitial extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ByteBuf copiedBuffer = Unpooled.copiedBuffer("$_".getBytes());
		DelimiterBasedFrameDecoder delimiterBasedFrameDecoder = new DelimiterBasedFrameDecoder(10240000, copiedBuffer);
		ch.pipeline().addLast(delimiterBasedFrameDecoder)
		             .addLast(new StringDecoder())
		             .addLast(new EchoServerHandler());
		
	}

}
