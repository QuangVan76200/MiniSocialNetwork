package com.example.demo.dto.request;

import java.util.List;

public class CleanCartRequest {
	private Long cartId;
	
	private List<Long> productIds;

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public List<Long> getProductIds() {
		return productIds;
	}

	public void setProductIds(List<Long> productIds) {
		this.productIds = productIds;
	}
	
	

}
