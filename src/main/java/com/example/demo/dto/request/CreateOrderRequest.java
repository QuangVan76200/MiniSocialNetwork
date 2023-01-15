package com.example.demo.dto.request;

import java.util.List;

public class CreateOrderRequest {

	private List<OrderProductItem> ordersProductItems;

	public List<OrderProductItem> getOrdersProductItems() {
		return ordersProductItems;
	}

	public void setOrdersProductItems(List<OrderProductItem> ordersProductItems) {
		this.ordersProductItems = ordersProductItems;
	}

}
