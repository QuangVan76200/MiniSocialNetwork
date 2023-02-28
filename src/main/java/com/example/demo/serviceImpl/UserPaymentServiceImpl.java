package com.example.demo.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.IUserPaymentDao;
import com.example.demo.entity.UserPayment;
import com.example.demo.service.IUserPaymentService;

@Service
public class UserPaymentServiceImpl implements IUserPaymentService {
	
	@Autowired
	private IUserPaymentDao userPaymentDao;

	@Override
	public UserPayment findById(Long id) {
		return userPaymentDao.findById(id).orElse(null);
	}
	@Override
	public void deleteById(Long id) {
		userPaymentDao.deleteById(id);
	}

}
