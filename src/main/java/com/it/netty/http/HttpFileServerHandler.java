package com.it.netty.http;

import java.io.File;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Pattern;

import javax.activation.MimetypesFileTypeMap;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelProgressiveFuture;
import io.netty.channel.ChannelProgressiveFutureListener;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;

public class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
 
	private  String   url  ;
	
	
	public HttpFileServerHandler(String url) {
		this.url = url;
	}


	@Override
	protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
		// TODO Auto-generated method stub
		if(!request.getDecoderResult().isSuccess()) {
			sendError(ctx, HttpResponseStatus.BAD_REQUEST);
			return ;
		}
		if(!request.getMethod().equals(HttpMethod.GET)) {
			sendError(ctx, HttpResponseStatus.METHOD_NOT_ALLOWED);
			return ;
		}
		
		String uri = request.getUri();
		String path = sanitizeUri(uri);
		if(path == null) {
			sendError(ctx, HttpResponseStatus.FORBIDDEN);
			return;
		}
		File file = new File(path);
		if(file.isHidden() || !file.exists()) {
			sendError(ctx, HttpResponseStatus.NOT_FOUND);
			return ;
		}
		if(file.isDirectory()) {
			if(uri.endsWith("/")) {
				sendListing(ctx, file);
			}else {
				sendRedirect(ctx, uri+"/");
			}
			return;
		}
		if(!file.isFile()) {
			sendError(ctx, HttpResponseStatus.FORBIDDEN);
			return ;
		}
		RandomAccessFile randomAccessFile = null;
		    try {
				randomAccessFile = new RandomAccessFile(file, "r");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				sendError(ctx, HttpResponseStatus.NOT_FOUND);
				return;
			}
		    long fileLength = randomAccessFile.length();
		    DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
		    defaultFullHttpResponse.headers().set(HttpHeaders.Names.CONTENT_LENGTH, fileLength);
		    MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
		    defaultFullHttpResponse.headers().set(HttpHeaders.Names.CONTENT_TYPE, mimetypesFileTypeMap.getContentType(file));
//		    defaultFullHttpResponse.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/html;charset=UTF-8");
		    defaultFullHttpResponse.headers().set("Content-Disposition", "attachment; filename="+file.getName());
		    if(request.headers().get(HttpHeaders.Names.CONNECTION).equals( HttpHeaders.Values.KEEP_ALIVE.toString())) {
		    	defaultFullHttpResponse.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
		    }
		    ctx.write(defaultFullHttpResponse);
		    ChannelFuture sendFileFuture =    ctx.writeAndFlush(new ChunkedFile(randomAccessFile,0,fileLength,8192),ctx.newProgressivePromise());
//		    ChannelFuture sendFileFuture = ctx.write(new ChunkedFile(randomAccessFile,0,fileLength,8192),ctx.newProgressivePromise());
//		    ChannelFuture sendFileFuture = ctx.write(new ChunkedFile(randomAccessFile),ctx.newProgressivePromise());
		    sendFileFuture.addListener(new ChannelProgressiveFutureListener() {
		    	@Override
				public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) throws Exception {
					// TODO Auto-generated method stub
					   if(total <0) {
						   System.err.println("Transfer progress: "+ progress);
					   }else {
						    System.err.println("Transfer progress: "+ progress +"/"+total);
					   }
				}
				@Override
				public void operationComplete(ChannelProgressiveFuture future) throws Exception {
					// TODO Auto-generated method stub
					System.out.println("Transfer progress Complete");
				}
			});
		    ChannelFuture channelFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
		    if(!request.headers().get(HttpHeaders.Names.CONNECTION).equals( HttpHeaders.Values.KEEP_ALIVE.toString())) {
		    	channelFuture.addListener(ChannelFutureListener.CLOSE);
		    }
		    
		
	}
	
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
		 if(ctx.channel().isActive()) {
			sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
		 }
	}
	
	
	
	private static final Pattern INSERCURE_URI = Pattern.compile(".*[<>&\"].*");
	
	private static final Pattern ALLOWED_FILE_NAME = Pattern.compile("[A-Za-z0-9][-_A-Za-z0-9\\.]*");
	public static void sendRedirect(ChannelHandlerContext ctx, String url) {
		DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FOUND);
		response.headers().set(HttpHeaders.Names.LOCATION, url);
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}
	
	
	
	
	public static void sendListing(ChannelHandlerContext ctx, File dir) {
		DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
		response.headers().set(HttpHeaders.Names.CONTENT_TYPE,"text/html;charset=UTF-8");
		StringBuilder sb = new StringBuilder();
		String dirPath = dir.getPath();
		sb.append("<!DOCTYPE html>\r\n")
		  .append("<html><head><title>")
		  .append(dirPath)
		  .append("Ä¿Â¼£º")
		  .append("</title></head><body>\r\n")
		  .append("<h3>\r\n")
		  .append(dirPath).append("Ä¿Â¼£º")
		  .append("</h3>\r\n")
		  .append("<ul>");
		   for (File f : dir.listFiles()) {
			if(f.isHidden() || !f.canRead()) {
				continue;
			}
			String name = f.getName();
			if(!ALLOWED_FILE_NAME.matcher(name).matches()) {
				continue;
			}
			sb.append("<li>Á´½Ó£º<a href=\"")
			  .append(name)
			  .append("\">")
			  .append(name)
			  .append("</a></li>\r\n");
		  }
		   sb.append("</ul></body></html>\r\n");
		   ByteBuf buf = Unpooled.copiedBuffer(sb, CharsetUtil.UTF_8);
		   
		   response.content().writeBytes(buf);
		   buf.release();
		  
		   ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
		
	}
	
	public  String sanitizeUri(String uri) {
		try {
			uri = URLDecoder.decode(uri, CharsetUtil.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			try {
				uri = URLDecoder.decode(uri, CharsetUtil.ISO_8859_1.name());
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(!uri.startsWith(url)) {
			return null;
		}
		if(!uri.startsWith("/")) {
			return null;
		}
		uri = uri.replace('/', File.separatorChar);
		if(uri.contains(File.separator+".")||uri.startsWith(".")||uri.endsWith(".")||INSERCURE_URI.matcher(uri).matches()) {
			return null;
		}
		return System.getProperty("user.dir")+uri;
	}
   public void sendError(ChannelHandlerContext ctx,HttpResponseStatus status) {
	   DefaultFullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer("Failure: "+ status.toString()+"\r\n",CharsetUtil.UTF_8));
	   fullHttpResponse.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain; charset=UTF-8");
	   ctx.writeAndFlush(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
   }
}
