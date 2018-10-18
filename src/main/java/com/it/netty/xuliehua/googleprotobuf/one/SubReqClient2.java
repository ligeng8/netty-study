package com.it.netty.xuliehua.googleprotobuf.one;

import java.net.InetSocketAddress;

import com.it.netty.xuliehua.googleprotobuf.SubReqClientHandler;
import com.it.netty.xuliehua.googleprotobuf.SubscribeRespProto;
import com.it.netty.xuliehua.googleprotobuf.SubscribeRespProto.SubscribeResp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

public class SubReqClient2 {

	
	public static void main(String[] args) {
		NioEventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group)
			         .channel(NioSocketChannel.class)
			         .option(ChannelOption.TCP_NODELAY, true)
			         .handler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							// TODO Auto-generated method stub
							ch.pipeline().addLast(new ProtobufVarint32FrameDecoder())
							             //.addLast(new ProtobufDecoder(SubscribeRespProto.SubscribeResp.getDefaultInstance()))
							             .addLast(new ProtobufVarint32LengthFieldPrepender())
							             .addLast(new SubRespDecode())
							             .addLast(new ObjectEncode())
							              .addLast(new SubReqClientHandler2());
						}
					});
			ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress("127.0.0.1", 9999)).sync();
			channelFuture.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			group.shutdownGracefully();
		}
		
	}
}
