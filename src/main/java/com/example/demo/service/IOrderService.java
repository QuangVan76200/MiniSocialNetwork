package com.example.demo.service;

import com.example.demo.dto.request.GetAllMyOrdersDTO;
import com.example.demo.dto.request.OrderProductItem;
import com.example.demo.dto.respone.ResponseMessage;
import com.example.demo.entity.BillingAddress;
import com.example.demo.entity.OrderDetails;
import com.example.demo.entity.Payment;
import com.example.demo.entity.ShippingAddress;

public interface IOrderService {

//	OrderDetails createOrder(CreateToOrderRequestDTO orderRequest) throws ResponseMessage;

	OrderDetails createOrderItems(Long cartItemId, OrderProductItem orderItem, ShippingAddress shippingAddress,
			BillingAddress billingAddress,
			Payment payment,
			String shippingMethod)  throws ResponseMessage;

	void cancelOrder(Long orderDetailsId) throws ResponseMessage;

	OrderDetails getAllOrders(GetAllMyOrdersDTO listOrderRequest) throws ResponseMessage;
}
