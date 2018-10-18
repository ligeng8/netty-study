package com.it.netty.xuliehua.googleprotobuf.one;

import com.it.netty.xuliehua.bean.SubscribeReq;
import com.it.netty.xuliehua.bean.SubscribeResp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ObjectEncode extends MessageToByteEncoder<Object> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
		// TODO Auto-generated method stub
		byte[] bytes = null;
		if(msg instanceof SubscribeReq) {
			SubscribeReq scbr = (SubscribeReq) msg;
			bytes = SerializeUtils.serialize(scbr, SubscribeReq.class);
		}else if(msg instanceof SubscribeResp){
			SubscribeResp scbp = (SubscribeResp) msg;
			bytes = SerializeUtils.serialize(scbp, SubscribeResp.class);
		}else {
			return;
		}
		out.writeBytes(bytes);
	}

}
