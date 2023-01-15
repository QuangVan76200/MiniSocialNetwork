package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.CancelOrderDTO;
import com.example.demo.dto.request.CreateOrderRequest;
import com.example.demo.dto.request.CreateToOrderRequestDTO;
import com.example.demo.dto.respone.ResponseMessage;
import com.example.demo.entity.Order;
import com.example.demo.entity.Response;
import com.example.demo.entity.ShoppingCart;
import com.example.demo.service.IOrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {

	@Autowired
	private IOrderService orderService;
	
	@PostMapping("/ceate_my_order")
	public ResponseEntity<?> createShoppingCart(@RequestBody CreateToOrderRequestDTO orderRequest) {
		try {
			Order myOrder = orderService.createOrder(orderRequest);
			return ResponseEntity.status(HttpStatus.OK).body(new Response("OK", "Create sucessfully", myOrder));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("FAILED", e.getMessage(), null));
		}
	}

	@PostMapping("/create_order")
	public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest request) {

		try {
			Order newOrder = orderService.createOrderItems(request);
			return ResponseEntity.status(HttpStatus.OK).body(new Response("OK", "Create successfully", newOrder));
		} catch (ResponseMessage e) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Failed", e.getMessage(), null));
		}
	}
	
	@PostMapping("/cancel_order")
	public ResponseEntity<?> cancelOrder( @RequestBody CancelOrderDTO cancelRequest){
		try {
			orderService.cancelOrder(cancelRequest.getProductId(), cancelRequest.getOrderId());
			return ResponseEntity.status(HttpStatus.OK).body(new Response("OK","Delete Successfull",cancelRequest.getOrderId()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Failed", e.getMessage(),null));
		}
		
	}

}
