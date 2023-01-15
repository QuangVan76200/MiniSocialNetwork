package com.example.demo.entity;

import java.util.Set;

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

@Entity
@Table(name = "shoppingCart")
public class ShoppingCart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cartId;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cartId")
	private Set<CartItem> listCartItem;

	@Column(name = "quantity")
	private Long quantity;

	@Column(name = "totalPayment")
	private Double totalPayment;

	@ManyToOne
	@JoinColumn(name = "userId", nullable = false, referencedColumnName = "userId")
	private User user;

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public Set<CartItem> getListCartItem() {
		return listCartItem;
	}

	public void setListCartItem(Set<CartItem> listCartItem) {
		this.listCartItem = listCartItem;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Double getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(Double totalPayment) {
		this.totalPayment = totalPayment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void addItemToCart(CartItem cartItem) {
		listCartItem.add(cartItem);
	}

	public void removeCartItem(CartItem cartItem) {
		listCartItem.remove(cartItem);
	}

	@Override
	public String toString() {
		return "ShoppingCart [cartId=" + cartId + ", listCartItem=" + listCartItem + ", quantity=" + quantity
				+ ", totalPayment=" + totalPayment + ", user=" + user + "]";
	}

}
