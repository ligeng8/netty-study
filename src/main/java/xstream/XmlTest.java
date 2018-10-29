package xstream;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;

public class XmlTest {
    public static void main(String[] args) {
   	 //创建user对象与customer对象并赋值
    	String s1 = "<user name=\"beyondLi\">\r\n" + 
    			"  <age>23</age>\r\n" + 
    			"  <customer>\r\n" + 
    			"    <customer>\r\n" + 
    			"      <commodity>商品1</commodity>\r\n" + 
    			"    </customer>\r\n" + 
    			"    <customer>\r\n" + 
    			"      <commodity>商品2</commodity>\r\n" + 
    			"    </customer>\r\n" + 
    			"  </customer>\r\n" + 
    			"</user>";
    	 XStream xStream = new XStream();
         //调用toXML 将对象转成字符串
         xStream.autodetectAnnotations(true);
         xStream.processAnnotations(new Class[]{User.class,Customer.class});
         Object object = xStream.fromXML(s1);
         System.out.println(object.getClass());
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
       String s = xStream.toXML(user);
       Object fromXML = xStream.fromXML(s);
       System.out.println(fromXML);
       System.out.println(s);

	}
}
