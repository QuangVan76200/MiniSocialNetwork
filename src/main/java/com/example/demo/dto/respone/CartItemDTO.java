package com.example.demo.dto.respone;

import com.example.demo.entity.CartItem;
import com.example.demo.entity.ShoppingCart;

public class CartItemDTO {
	private Long id;
	private Long productId;
	private String productName;
	private Double pricePerUnit;
	private int quantity;
	private Double totalPrice;

	public CartItemDTO(Long id, Long productId, String productName, Double pricePerUnit, int quantity,
			Double totalPrice) {
		this.id = id;
		this.productId = productId;
		this.productName = productName;
		this.pricePerUnit = pricePerUnit;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
	}

	public CartItemDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(Double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public static CartItemDTO fromEntity(CartItem cartItem) {
		CartItemDTO cartItemDTO = new CartItemDTO();
		cartItemDTO.setId(cartItem.getCartItemId());
		cartItemDTO.setProductName(cartItem.getProduct().getName());
		cartItemDTO.setPricePerUnit(cartItem.getProduct().getPricePerUnit());
		cartItemDTO.setQuantity(cartItem.getQuantity());
		cartItemDTO.setTotalPrice(cartItem.getTotalPrice());
		return cartItemDTO;
	}
//    public static ShoppingCartDTO fromEntity(ShoppingCart shoppingCart) {
//		ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
//		shoppingCartDTO.setShoppingCartId(shoppingCart.getCartId());
//		shoppingCartDTO.setUserId(shoppingCart.getUser().getId());
//		shoppingCart.setTotalPayment(shoppingCart.getTotalPayment());
//		return shoppingCartDTO;
//	}
}
