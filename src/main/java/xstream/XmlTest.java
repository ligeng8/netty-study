package xstream;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;

public class XmlTest {
    public static void main(String[] args) {
   	 //����user������customer���󲢸�ֵ
    	String s1 = "<user name=\"beyondLi\">\r\n" + 
    			"  <age>23</age>\r\n" + 
    			"  <customer>\r\n" + 
    			"    <customer>\r\n" + 
    			"      <commodity>��Ʒ1</commodity>\r\n" + 
    			"    </customer>\r\n" + 
    			"    <customer>\r\n" + 
    			"      <commodity>��Ʒ2</commodity>\r\n" + 
    			"    </customer>\r\n" + 
    			"  </customer>\r\n" + 
    			"</user>";
    	 XStream xStream = new XStream();
         //����toXML ������ת���ַ���
         xStream.autodetectAnnotations(true);
         xStream.processAnnotations(new Class[]{User.class,Customer.class});
         Object object = xStream.fromXML(s1);
         System.out.println(object.getClass());
       User user = new User();
       Customer customer1 = new Customer();
       Customer customer2 = new Customer();
       customer1.setCommodity("��Ʒ1");
       customer2.setCommodity("��Ʒ2");
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
