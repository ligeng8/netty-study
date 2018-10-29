package com.it.netty.websocket;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;
import xstream.Customer;
import xstream.User;

public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

	private WebSocketServerHandshaker handlershaker;
	/**
	 * ip 连接
	 */
	private Map<String, ChannelHandlerContext> content = new ConcurrentHashMap<>();
	/**
	 * 
	 */
	private Map<String, List<ChannelHandlerContext>> subAll = new ConcurrentHashMap<>();

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 传统的http接入
		if (msg instanceof FullHttpRequest) {
			handleHttpRequest(ctx, (FullHttpRequest) msg);
		} else if (msg instanceof WebSocketFrame) {// websocket 接入
			handleWebsocketFrame(ctx, (WebSocketFrame) msg);
		}
	}

	public void handleWebsocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
		if (frame instanceof CloseWebSocketFrame) {// 判断是否是关闭链路的命令
			handlershaker.close(ctx.channel(), (CloseWebSocketFrame) (frame.retain()));
			return;
		} else if (frame instanceof PingWebSocketFrame) {// 判断是否是ping消息
			ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
			return;
		}

		if (!(frame instanceof TextWebSocketFrame)) {
			throw new UnsupportedOperationException(
					String.format("%s frame types not supported", frame.getClass().getName()));
		}
		String text = ((TextWebSocketFrame) frame).text();
		Request request = JSON.parseObject(text, Request.class);
		if (request.getType().equals(RequestType.sub)) {
			if (!subAll.containsKey(request.getMessage())) {
				List<ChannelHandlerContext> arrayList = new ArrayList<>();
				arrayList.add(ctx);
				subAll.put(request.getMessage(), arrayList);
			} else {
				List<ChannelHandlerContext> list = subAll.get(request.getMessage());
				list.add(ctx);
			}
		} else if (request.getType().equals(RequestType.send)) {

			System.out.println(text);
			ctx.channel().write(
					new TextWebSocketFrame(text + ", 欢迎使用Netty WebSocket服务，现在时刻：" + new Date().toLocaleString()));
		} else {
			User user = new User();
			Customer customer1 = new Customer();
			Customer customer2 = new Customer();
			customer1.setCommodity("商品1");
			customer2.setCommodity("商品2");
			List<Customer> list = new ArrayList<>();
			list.add(customer1);
			list.add(customer2);
			user.setName("beyondLi");
			user.setAge(23);
			user.setCustomer(list);
			ctx.channel().write(new TextWebSocketFrame(JSON.toJSONString(user)));
		}

	}

	public void sendhttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {

		if (res.getStatus().code() != 200) {
			ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8);
			res.content().writeBytes(buf);
			buf.release();
			res.headers().set(HttpHeaders.Names.CONTENT_LENGTH, res.content().readableBytes());
		}
		ChannelFuture channelFuture = ctx.channel().writeAndFlush(res);
		String connection = req.headers().get(HttpHeaders.Names.CONNECTION);
		// 非keep-alive 关闭连接
		if (!"keep-alive".equals(connection) || res.getStatus().code() != 200) {
			channelFuture.addListener(ChannelFutureListener.CLOSE);
		}

	}

	public void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
//		String uri2 = req.getUri();
//		String url = uri2.substring(0, uri2.indexOf("?"));
//		if(!"/websocket/info".equals(url)) {
		if (!req.getDecoderResult().isSuccess() || !"websocket".equals(req.headers().get("Upgrade"))  ) {
			sendhttpResponse(ctx, req,
					new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
			return;
		}
//		}
		
//		if(url.equals("/websocket/info")) {
//			
//			String resmessage ="{\"entropy\":-1030028639,\"origins\":[\"*:*\"],\"cookie_needed\":true,\"websocket\":true}";
//			DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
//			ByteBuf buf = Unpooled.copiedBuffer(resmessage, CharsetUtil.UTF_8);
//			response.content().writeBytes(buf);
//			buf.release();
//			 ctx.channel().writeAndFlush(response);
//		}
		
		WebSocketServerHandshakerFactory handshakerFactory = new WebSocketServerHandshakerFactory(
				"ws://localhost:9999/websocket", null, false);
		handlershaker = handshakerFactory.newHandshaker(req);
		if (handlershaker == null) {
			WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
		} else {
			handlershaker.handshake(ctx.channel(), req);
			String uri = req.getUri();

		}

	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		ctx.flush();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelInactive(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		ctx.close();
	}
}
