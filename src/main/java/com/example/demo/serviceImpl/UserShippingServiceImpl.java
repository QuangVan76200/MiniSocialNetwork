package com.example.demo.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.IUserShippingDao;
import com.example.demo.entity.UserShipping;
import com.example.demo.service.IUserShippingService;

@Service
public class UserShippingServiceImpl implements IUserShippingService{
	
	@Autowired
	private IUserShippingDao userShippingDao;

	@Override
	public UserShipping findById(Long id) {
		return userShippingDao.findById(id).orElse(null);
	}

	@Override
	public void deleteById(Long id) {
		userShippingDao.deleteById(id);
	}

}
