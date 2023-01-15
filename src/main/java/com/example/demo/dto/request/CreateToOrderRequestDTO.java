package com.example.demo.dto.request;

import javax.validation.constraints.NotBlank;

public class CreateToOrderRequestDTO {

	@NotBlank(message = "userId is required to create user order")
	private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
