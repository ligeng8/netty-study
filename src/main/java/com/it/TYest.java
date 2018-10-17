package com.it;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

public class TYest {
	public static void main(String[] args) throws IOException {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(100);
		userInfo.setUserName("welcome in netty");
		long currentTimeMillis = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
			outputStream.writeObject(userInfo);
			outputStream.flush();
			outputStream.close();
			byte[] byteArray = byteArrayOutputStream.toByteArray();
			byteArrayOutputStream.close();
		}
		System.out.println(System.currentTimeMillis()-currentTimeMillis);
		long currentTimeMillis1 = System.currentTimeMillis();
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		for (int i = 0; i < 1000000; i++) {
			byte[] codc = userInfo.codc(buffer);
		}
		System.out.println(System.currentTimeMillis()-currentTimeMillis1);
	}
}
