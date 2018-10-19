package com.it.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class HttpFileServer {

	private static final String DEFAULT_URL = "/src/main/java";
	
	public static void main(String[] args) {
		NioEventLoopGroup bossGroup = new NioEventLoopGroup();
		NioEventLoopGroup workGroup = new NioEventLoopGroup();
		
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workGroup)
			         .channel(NioServerSocketChannel.class)
			         .handler(new LoggingHandler(LogLevel.INFO))
			         .childHandler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							// TODO Auto-generated method stub
							ch.pipeline().addLast("http-decoder",new HttpRequestDecoder())
							             .addLast("http-aggregator",new HttpObjectAggregator(65536))
							             .addLast("http-encoder",new  HttpResponseEncoder())
							             .addLast("http-chunked",new ChunkedWriteHandler())
							             .addLast(new HttpFileServerHandler(DEFAULT_URL));
						}
					});
			
			ChannelFuture channelFuture = bootstrap.bind(9999).sync();
			System.err.println("http server start");
			channelFuture.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}
}
