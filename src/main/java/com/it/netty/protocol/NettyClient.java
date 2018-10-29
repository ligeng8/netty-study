package com.it.netty.protocol;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.it.netty.protocol.client.HeartBeatReqHandler;
import com.it.netty.protocol.client.LoginAuthReqHandler;
import com.it.netty.protocol.common.MarShallingCodecFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
	
	
	
	
	public static void main(String[] args) {
		NettyClient nettyClient = new NettyClient();
		nettyClient.connection("127.0.0.1", 9999);
		nettyClient.group.shutdownGracefully();
	}
	ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();

	public  EventLoopGroup group = new NioEventLoopGroup();

	private volatile int  count = 0;
	
	public void connection(String ip, int port) {

		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							// TODO Auto-generated method stub
							ch.pipeline().addLast(MarShallingCodecFactory.buildMarshallingDecoder())
									.addLast(MarShallingCodecFactory.buildMarshallingEncoder())
									.addLast(new LoginAuthReqHandler()).addLast(new HeartBeatReqHandler());
						}
					});
			ChannelFuture channelFuture = bootstrap
					.connect(new InetSocketAddress(ip, port), new InetSocketAddress("127.0.0.1", 8888)).sync();
			channelFuture.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			count = count + 1;
			if(count > 5) {
				System.out.println(count);
				System.exit(-1);
			}else {
				System.out.println(count);
			}
					
			newSingleThreadExecutor.execute(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(count > 5) {
						System.out.println(count);
						throw new RuntimeException("·þÎñ¶ËÒÑ¹Ò");
					}
					try {
						Thread.sleep(5000);
						connection(ip, port);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
	}
	
}
