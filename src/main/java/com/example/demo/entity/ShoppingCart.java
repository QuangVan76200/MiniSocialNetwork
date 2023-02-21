package com.example.demo.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "shoppingCart")
public class ShoppingCart implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cartId;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "shoppingCartId", fetch = FetchType.EAGER)
	private Set<CartItem> listCartItem;

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
		return "ShoppingCart [cartId=" + cartId + ", listCartItem=" + listCartItem + ", quantity=" + ", totalPayment="
				+ totalPayment + ", user=" + user + "]";
	}

}
