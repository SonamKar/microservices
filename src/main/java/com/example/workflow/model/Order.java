package com.example.workflow.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Order {
	@JsonProperty("orderid")
	String orderid;
	@JsonProperty("ordername")
	String ordername;

	public Order()
	{

	}
	public Order(String orderid,String ordername)
	{
		this.orderid=orderid;
		this.ordername=ordername;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getOrdername() {
		return ordername;
	}
	public void setOrdername(String ordername) {
		this.ordername = ordername;
	}

}
