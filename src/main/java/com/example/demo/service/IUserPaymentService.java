package com.example.demo.service;

import com.example.demo.entity.UserPayment;

public interface IUserPaymentService {

	UserPayment findById(Long id);

	void deleteById(Long id);

}
