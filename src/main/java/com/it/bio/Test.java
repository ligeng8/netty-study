package com.it.bio;

import java.io.IOException;
import java.util.Random;

public class Test { // 测试主方法
	public static void main(String[] args) throws InterruptedException {
		final char operators[] = { '+', '-', '*', '/' };
		final Random random = new Random(System.currentTimeMillis());
		// TODO Auto-generated method stub
		while (true) {
			// 随机产生算术表达式
			String expression = random.nextInt(10) + "" + operators[random.nextInt(4)] + (random.nextInt(10) + 1);
			Client.send(expression);
//			try {
//				Thread.currentThread().sleep(random.nextInt(1000));
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}

	}
}
