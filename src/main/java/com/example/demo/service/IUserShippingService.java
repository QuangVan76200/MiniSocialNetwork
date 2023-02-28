package com.example.demo.service;

import com.example.demo.entity.UserShipping;

public interface IUserShippingService {

	UserShipping findById(Long id);

	void deleteById(Long id);
}
