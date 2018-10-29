package com.it.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebSocketServer {

	public static void main(String[] args) {
		int port = 9999;
		NioEventLoopGroup bossGroup = new NioEventLoopGroup();
		NioEventLoopGroup workGroup = new NioEventLoopGroup();

		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workGroup)
//		            .option(option, value)
					.channel(NioServerSocketChannel.class).handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							// TODO Auto-generated method stub
							ch.pipeline().addLast("http-codec", new HttpServerCodec())
									.addLast("aggregator", new HttpObjectAggregator(65536))
									.addLast("http-chunked", new ChunkedWriteHandler())
									.addLast(new WebSocketServerHandler());
						}
					});
			ChannelFuture channelFuture = bootstrap.bind(port).sync();
			channelFuture.channel().closeFuture().sync();
		} catch (InterruptedException e) {

		} finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}
}
