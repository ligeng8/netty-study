package com.it.netty.xuliehua.googleprotobuf.one;

import java.util.List;

import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.it.netty.xuliehua.bean.SubscribeReq;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class SubReqDecode extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		byte[] bytes = new byte[in.readableBytes()];
		in.readBytes(bytes);
		RuntimeSchema<SubscribeReq> runtimeSchema = RuntimeSchema.createFrom(SubscribeReq.class);
		SubscribeReq newMessage = runtimeSchema.newMessage();
		ProtobufIOUtil.mergeFrom(bytes, newMessage, runtimeSchema);
		out.add(newMessage);
	}

}
