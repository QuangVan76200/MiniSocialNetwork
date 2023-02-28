package com.example.demo.dto.request;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class OrderProductItem {

	private Long orderId;

	private String customer;

	private ProductItemDTO productItem;

	private String orderAdress;

	private String numberPhone;

	@Enumerated(EnumType.STRING)
	private PaymentMethod PaymentMethod;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	private Double totalPrice;

	private Double quantity;

	public enum PaymentMethod {
		PAYMENT_ON_DELIVERY, PAYMENT_VIA_E_WALLET, PAYMENT_VIA_SMART_BANKING,

	}

	public enum OrderStatus {
		NEW, PROCESSING, SHIPPED, DELIVERED, CANCELLED
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
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

	public PaymentMethod getPaymentMethod() {
		return PaymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		PaymentMethod = paymentMethod;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	
	

}
