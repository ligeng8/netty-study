package com.it;
/**
 *
 * @author ligeng3
 *
 */
public class ClassUtil {

	
	/**
	 *  ��Object����ת��Ϊʵ�������
	 * @param object
	 * @param clazz
	 * @return
	 */
	public static <T> T transForClass(Object object, Class<T> clazz ) {
		if(clazz.isInstance(object)) {
			return clazz.cast(object);
		}
		return null;
	}
	
	
	public static<T> void ss(Object object, Class<T> clazz ) {
	}
	public static void main(String[] args) {
		User user = new User();
		User user2 = transForClass(user, User.class);
	}
}
