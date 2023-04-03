package com.example.demo.dto.respone;

import java.util.List;

import com.example.demo.entity.ShoppingCart;

public class ShoppingCartDTO {

	private Long shoppingCartId;

	private Long userId;

	private Double totalPayment;

	private List<CartItemDTO> cartItemDTO;

	public ShoppingCartDTO(Long shoppingCartId, Long userId, Double totalPayment, List<CartItemDTO> cartItemDTO) {
		this.shoppingCartId = shoppingCartId;
		this.userId = userId;
		this.totalPayment = totalPayment;
		this.cartItemDTO = cartItemDTO;
	}

	public ShoppingCartDTO() {
	}

	public Long getShoppingCartId() {
		return shoppingCartId;
	}

	public void setShoppingCartId(Long shoppingCartId) {
		this.shoppingCartId = shoppingCartId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Double getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(Double totalPayment) {
		this.totalPayment = totalPayment;
	}

	public List<CartItemDTO> getCartItemDTO() {
		return cartItemDTO;
	}

	public void setCartItemDTO(List<CartItemDTO> cartItemDTO) {
		this.cartItemDTO = cartItemDTO;
	}

	public static ShoppingCartDTO fromEntity(ShoppingCart shoppingCart) {
		ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
		shoppingCartDTO.setShoppingCartId(shoppingCart.getCartId());
		shoppingCartDTO.setUserId(shoppingCart.getUser().getId());
		shoppingCart.setTotalPayment(shoppingCart.getTotalPayment());
		return shoppingCartDTO;
	}

}
