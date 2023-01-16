package com.example.demo.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "productOrder")
	private List<OrderDetails> orderItems;

	@Column(name = "createdAt")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdAt;

	@Column(name = "updatedAt")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updatedAt;

	@Column(name = "orderAdress")
	private String orderAdress;

	@Column(name = "numberPhone")
	private String numberPhone;

	@ManyToOne
	@JoinColumn(name = "userId", nullable = false, referencedColumnName = "userId")
	private User user;

	@Column(name = "paypalOrderId")
	private String paypalOrderId;

	@Column(name = "paypalOrderStatus")
	private String paypalOrderStatus;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public List<OrderDetails> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderDetails> orderItems) {
		this.orderItems = orderItems;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPaypalOrderId() {
		return paypalOrderId;
	}

	public void setPaypalOrderId(String paypalOrderId) {
		this.paypalOrderId = paypalOrderId;
	}

	public String getPaypalOrderStatus() {
		return paypalOrderStatus;
	}

	public void setPaypalOrderStatus(String paypalOrderStatus) {
		this.paypalOrderStatus = paypalOrderStatus;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderItems=" + orderItems + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + ", orderAdress=" + orderAdress + ", numberPhone=" + numberPhone + ", user=" + user + "]";
	}

}
