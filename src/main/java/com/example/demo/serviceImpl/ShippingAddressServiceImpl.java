package com.example.demo.serviceImpl;

import org.springframework.stereotype.Service;

import com.example.demo.entity.ShippingAddress;
import com.example.demo.entity.UserShipping;
import com.example.demo.service.IShippingAddressService;

@Service
public class ShippingAddressServiceImpl implements IShippingAddressService {

	public ShippingAddress setByUserShipping(UserShipping userShipping, ShippingAddress shippingAddress) {

		shippingAddress.setShippingAddressName(userShipping.getUserShippingName());
		shippingAddress.setShippingAddressStreet1(userShipping.getUserShippingStreet1());
		shippingAddress.setShippingAddressStreet2(userShipping.getUserShippingStreet2());
		shippingAddress.setShippingAddressCity(userShipping.getUserShippingCity());
		shippingAddress.setShippingAddressState(userShipping.getUserShippingState());
		shippingAddress.setShippingAddressCountry(userShipping.getUserShippingCountry());
		shippingAddress.setShippingAddressZipcode(userShipping.getUserShippingZipcode());

		return shippingAddress;
	}
}
