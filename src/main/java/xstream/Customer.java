package xstream;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by beyondLi on 2017/6/14.
 */
@XStreamAlias("customer")
public class Customer {
    private String commodity;

	public String getCommodity() {
		return commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}
    
}