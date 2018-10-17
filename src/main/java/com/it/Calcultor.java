package com.it;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;

public class Calcultor {

	
	public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException {
		
		
		OutputStream out = new FileOutputStream("e://tomcat.ss");
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(out );
		User user = new User();
		user.setAge("23");
		user.setName("tom");
		objectOutputStream.writeObject(user);
		objectOutputStream.close();
		
		ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("e://tomcat.ss"));
		Object readObject = objectInputStream.readObject();
		Field[] fields = readObject.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			String name = field.getName();
			field.set(readObject, "value");
			Class<?> type = field.getType();
		}
		System.out.println(readObject.getClass().getDeclaredFields());
	}
	private static Double cal(String expression ,double sum) {
		if (expression.indexOf('+') != -1) {
			String substring = expression.substring(0, expression.indexOf('+'));
			sum = sum + Double.valueOf(substring);
			cal(expression.substring(expression.indexOf('+')+1), sum);

		} else if (expression.indexOf('-') != -1) {
			String substring = expression.substring(0, expression.indexOf('-'));
			sum = sum - Double.valueOf(substring);
			cal(expression.substring(expression.indexOf('-')+1), sum);
		} else if (expression.indexOf('*') != -1) {
			String substring = expression.substring(0, expression.indexOf('*'));
			sum = sum * Double.valueOf(substring);
			cal(expression.substring(expression.indexOf('*')+1), sum);
		} else if (expression.indexOf('/') != -1) {
			String substring = expression.substring(0, expression.indexOf('/'));
			sum = sum / Double.valueOf(substring);
			cal(expression.substring(expression.indexOf('/')+1), sum);
		}
		return sum;
	}
	
	
	public static Double cal(String expression ) {
		return cal( expression , 0) ;
	}
}
