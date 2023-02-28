package com.example.demo.service;

import com.example.demo.entity.BillingAddress;
import com.example.demo.entity.UserBilling;

public interface IBillingAddressService {

	BillingAddress setByUserBilling(UserBilling userBilling, BillingAddress billingAddress);
}
