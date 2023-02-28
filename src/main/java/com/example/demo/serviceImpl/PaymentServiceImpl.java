package com.example.demo.serviceImpl;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Payment;
import com.example.demo.entity.UserPayment;
import com.example.demo.service.IPaymentService;

@Service
public class PaymentServiceImpl implements IPaymentService {

	public Payment setByUserPayment(UserPayment userPayment, Payment payment) {

		payment.setType(userPayment.getType());
		payment.setHolderName(userPayment.getHolderName());
		payment.setCardNumber(userPayment.getCardNumber());
		payment.setExpiryMonth(userPayment.getExpiryMonth());
		payment.setExpiryYear(userPayment.getExpiryYear());
		payment.setCvc(userPayment.getCvc());

		return payment;
	}
}
