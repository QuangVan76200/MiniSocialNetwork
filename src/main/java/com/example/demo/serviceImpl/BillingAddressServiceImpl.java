package com.example.demo.serviceImpl;

import org.springframework.stereotype.Service;

import com.example.demo.entity.BillingAddress;
import com.example.demo.entity.UserBilling;
import com.example.demo.service.IBillingAddressService;


@Service
public class BillingAddressServiceImpl implements IBillingAddressService {

	public BillingAddress setByUserBilling(UserBilling userBilling, BillingAddress billingAddress) {

		billingAddress.setBillingAddressName(userBilling.getUserBillingName());
		billingAddress.setBillingAddressStreet1(userBilling.getUserBillingStreet1());
		billingAddress.setBillingAddressStreet2(userBilling.getUserBillingStreet2());
		billingAddress.setBillingAddressCity(userBilling.getUserBillingCity());
		billingAddress.setBillingAddressState(userBilling.getUserBillingState());
		billingAddress.setBillingAddressCountry(userBilling.getUserBillingCountry());
		billingAddress.setBillingAddressZipcode(userBilling.getUserBillingZipcode());

		return billingAddress;
	}

}
