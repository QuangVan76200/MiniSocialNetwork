package com.example.demo.dto.request;

public class OrderProductItem {

	private Long orderId;

	private long quantity;

	private ProductItemDTO productItem;

	private String orderAdress;

	private String numberPhone;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public ProductItemDTO getProductItem() {
		return productItem;
	}

	public void setProductItem(ProductItemDTO productItem) {
		this.productItem = productItem;
	}

	public String getOrderAdress() {
		return orderAdress;
	}

	public void setOrderAdress(String orderAdress) {
		this.orderAdress = orderAdress;
	}

	public String getNumberPhone() {
		return numberPhone;
	}

	public void setNumberPhone(String numberPhone) {
		this.numberPhone = numberPhone;
	}

}
