package com.it.netty.xuliehua.jbossMarshaling;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class SubClient {
	public static void main(String[] args) {
		NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(nioEventLoopGroup).channel(NioSocketChannel.class)
			 .handler(new LoggingHandler(LogLevel.INFO))
			.option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							// TODO Auto-generated method stub
							ch.pipeline().addLast(MarShallingCodecFactory.buildMarshallingDecoder())
									.addLast(MarShallingCodecFactory.buildMarshallingEncoder())
									.addLast(new SubReqClientHandler());
						}
					});

			ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress("127.0.0.1", 9999)).sync();
			channelFuture.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			nioEventLoopGroup.shutdownGracefully();
		}
	}
}
