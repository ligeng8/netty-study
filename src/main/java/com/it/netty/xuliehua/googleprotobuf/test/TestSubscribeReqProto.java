package com.it.netty.xuliehua.googleprotobuf.test;

import java.util.ArrayList;
import java.util.List;

import com.google.protobuf.InvalidProtocolBufferException;
import com.it.netty.xuliehua.googleprotobuf.SubscribeReqProto;
import com.it.netty.xuliehua.googleprotobuf.SubscribeReqProto.SubscribeReq;
import com.it.netty.xuliehua.googleprotobuf.SubscribeReqProto.SubscribeReq.Builder;

public class TestSubscribeReqProto {

	
	public static void main(String[] args) throws InvalidProtocolBufferException {
		SubscribeReq subscribeReq = createSubscribeReq();
		System.out.println("Before encode:" + subscribeReq.toString());
		byte[] encode = encode(subscribeReq);
		System.out.println("encode size : "+ encode.length);
		SubscribeReq subscribeReq2 = decode(encode);
		System.out.println("After decode:"+subscribeReq2.toString() );
		System.out.println("Assert equal:"+subscribeReq2.equals(subscribeReq));
	}
	
	
	
	
	
	
	public static byte[] encode(SubscribeReqProto.SubscribeReq req) {
		
		return req.toByteArray();
	}
	
	public  static SubscribeReqProto.SubscribeReq decode(byte[] bytes) throws InvalidProtocolBufferException{
		return SubscribeReqProto.SubscribeReq.parseFrom(bytes);
	}
	
	public static SubscribeReqProto.SubscribeReq createSubscribeReq(){
		Builder newBuilder = SubscribeReqProto.SubscribeReq.newBuilder();
		newBuilder.setSubReqID(1)
		          .setUserName("ligeng")
		          .setPhoneNumber("18292811987")
		          .setProductName("Netty Book");
		List<String> values = new ArrayList<>();
		values.add("Beijing san");
		values.add("Nanjing shifu");
		values.add("hangzhou guozepeng");
		newBuilder.addAllAddress(values);
		return newBuilder.build();
	}
	
	
	
}
