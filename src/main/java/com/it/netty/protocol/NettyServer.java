package com.it.netty.protocol;

import com.it.netty.protocol.common.MarShallingCodecFactory;
import com.it.netty.protocol.server.HeartBeatResHandler;
import com.it.netty.protocol.server.LoginAuthRespHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class NettyServer {
	public static void main(String[] args) {
		NioEventLoopGroup bossGroup = new NioEventLoopGroup();
		NioEventLoopGroup workGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workGroup)
			         .channel(NioServerSocketChannel.class)
			         .option(ChannelOption.TCP_NODELAY, true)
			         .handler(new LoggingHandler(LogLevel.INFO))
			         .childHandler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel ch) throws Exception {

							ch.pipeline().addLast(MarShallingCodecFactory.buildMarshallingDecoder())
							             .addLast(MarShallingCodecFactory.buildMarshallingEncoder())
							             .addLast(new LoginAuthRespHandler())
							             .addLast(new HeartBeatResHandler());
						}
					});
			ChannelFuture channelFuture = bootstrap.bind(9999).sync();
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
