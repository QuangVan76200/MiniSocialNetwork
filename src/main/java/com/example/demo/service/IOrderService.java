package com.example.demo.service;

import com.example.demo.dto.request.CreateOrderRequest;
import com.example.demo.dto.request.CreateToOrderRequestDTO;
import com.example.demo.dto.request.GetAllMyOrdersDTO;
import com.example.demo.dto.respone.ResponseMessage;
import com.example.demo.entity.Order;

public interface IOrderService {

	Order createOrder(CreateToOrderRequestDTO orderRequest) throws ResponseMessage;

	Order createOrderItems(CreateOrderRequest orderRequest) throws ResponseMessage;

	void cancelOrder(Long orderDetailsId) throws ResponseMessage;

	Order getAllOrders(GetAllMyOrdersDTO listOrderRequest) throws ResponseMessage ;
}
