package com.example.demo.service;

import com.example.demo.entity.Payment;
import com.example.demo.entity.UserPayment;

public interface IPaymentService {
	Payment setByUserPayment(UserPayment userPayment, Payment payment);
}
