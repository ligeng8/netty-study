package com.it.netty.filetransfer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

import io.netty.util.CharsetUtil;

public class FileTest {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		File file = new File("001.html");
		InputStream inputStream = FileTest.class.getClassLoader().getResourceAsStream("index.html");
		System.out.println(file.getAbsolutePath());
		System.out.println(file.getPath());
		System.out.println(file.exists());
		try {
			RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
			FileChannel channel = randomAccessFile.getChannel();
			String str = null;
			while ((str = randomAccessFile.readLine()) != null) {
				String string = new String(str.getBytes(CharsetUtil.ISO_8859_1),CharsetUtil.UTF_8);
				System.out.println(string);
				if("</head>".equals(string)){
					randomAccessFile.writeUTF("Fucntosds");
				}
			}
			randomAccessFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
