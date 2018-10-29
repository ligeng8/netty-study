package com.it.netty.http.xml.pojo;

public class Order {

	private Integer orderNumber;
	
	private Customer customer;
	
	private Address billAddress;
	
	private Shipping shipping;
	
	private Address deliveryAddress;
	
	private Double total;

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Address getBillAddress() {
		return billAddress;
	}

	public void setBillAddress(Address billAddress) {
		this.billAddress = billAddress;
	}

	public Shipping getShipping() {
		return shipping;
	}

	public void setShipping(Shipping shipping) {
		this.shipping = shipping;
	}

	public Address getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(Address deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}


	
	
	
}
