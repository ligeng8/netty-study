package com.it.netty.http.xml.pojo;

import java.util.List;

public class Customer {
	private Long cid;
	private String firstName;

	private String name;

	private List<String> middleNames;

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}


	public String getName() {
		return name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getMiddleNames() {
		return middleNames;
	}

	public void setMiddleNames(List<String> middleNames) {
		this.middleNames = middleNames;
	}

	

}
