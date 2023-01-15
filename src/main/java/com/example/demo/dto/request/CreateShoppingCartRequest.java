package com.example.demo.dto.request;

import javax.validation.constraints.NotBlank;

public class CreateShoppingCartRequest {

	@NotBlank(message = "userId is required to create user shopping cart")
	private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
