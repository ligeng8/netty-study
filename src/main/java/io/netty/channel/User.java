package io.netty.channel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class User {
    @Stop(group="netty",module="user",intefaceClass="com.it.cn" )
	private String aa;
    
    
    public void ss() {
    	
    }

	private String bb;
    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		
    	User user = new User();
    	String name = user.getClass().getName();
    	Field[] declaredFields = user.getClass().getDeclaredFields();
    	
    	Method[] declaredMethods = user.getClass().getDeclaredMethods();
    	for (Method method : declaredMethods) {
    		Class<?> declaringClass = method.getDeclaringClass();
		}
    	for (Field field : declaredFields) {
    		Stop annotation = field.getAnnotation(Stop.class);
    		if(annotation == null) {
    			continue;
    		}
    		Class<?> type = field.getType();
    		field.setAccessible(true);
    		field.set(user, "1234567890");
    		System.out.println(annotation.group());
    		System.out.println(annotation.module());
    		System.out.println(annotation.intefaceClass());
    		System.out.println(annotation.export());
		}
	}
	
}
