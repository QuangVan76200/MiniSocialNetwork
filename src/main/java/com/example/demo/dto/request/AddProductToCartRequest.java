package com.example.demo.dto.request;

public class AddProductToCartRequest {

	private Long cartId;

	private int quantity;

	private ProductItemDTO productItem; 

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public ProductItemDTO getProductItem() {
		return productItem;
	}

	public void setProductItem(ProductItemDTO productItem) {
		this.productItem = productItem;
	}

}
