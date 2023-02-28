package com.example.demo.service;

import com.example.demo.entity.ShippingAddress;
import com.example.demo.entity.UserShipping;

public interface IShippingAddressService {

	ShippingAddress setByUserShipping(UserShipping userShipping, ShippingAddress shippingAddress);
}
