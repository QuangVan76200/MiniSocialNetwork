package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.CancelOrderDTO;
import com.example.demo.dto.request.CreateToOrderRequestDTO;
import com.example.demo.dto.request.GetAllMyOrdersDTO;
import com.example.demo.dto.request.OrderProductItem;
import com.example.demo.dto.respone.ResponseMessage;
import com.example.demo.entity.BillingAddress;
import com.example.demo.entity.OrderDetails;
import com.example.demo.entity.Payment;
import com.example.demo.entity.Response;
import com.example.demo.entity.ShippingAddress;
import com.example.demo.service.IOrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {

	@Autowired
	private IOrderService orderService;

//	@PostMapping("/ceate_my_order")
//	public ResponseEntity<?> createShoppingCart(@RequestBody CreateToOrderRequestDTO orderRequest) {
//		try {
//			OrderDetails myOrder = orderService.createOrder(orderRequest);
//			return ResponseEntity.status(HttpStatus.OK).body(new Response("OK", "Create sucessfully", myOrder));
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.OK).body(new Response("FAILED", e.getMessage(), null));
//		}
//	}

	@PostMapping("/create_order/{cartItemId}")
	public ResponseEntity<?> createOrder(@PathVariable Long cartItemId, @RequestBody OrderProductItem request, @RequestBody ShippingAddress shippingAddress,
			@RequestBody BillingAddress billingAddress,
			@RequestBody Payment payment,
			@RequestBody String shippingMethod) {

		try {
			OrderDetails newOrder = orderService.createOrderItems(cartItemId, request, shippingAddress, billingAddress, payment, shippingMethod);
			return ResponseEntity.status(HttpStatus.OK).body(new Response("OK", "Create successfully", newOrder));
		} catch (ResponseMessage e) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Failed", e.getMessage(), null));
		}
	}

	@PostMapping("/cancel_order")
	public ResponseEntity<?> cancelOrder(@RequestBody CancelOrderDTO cancelRequest) {
		try {
			orderService.cancelOrder(cancelRequest.getOrderDetailsId());
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response("OK", "Delete Successfull", cancelRequest.getOrderDetailsId()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Failed", e.getMessage(), null));
		}

	}

	@GetMapping("/list_all_orders")
	public ResponseEntity<?> getMyOrders(@RequestBody GetAllMyOrdersDTO listOrderRequest) {
		try {
			OrderDetails listMyOrders = orderService.getAllOrders(listOrderRequest);
			System.out.println("listMyOrders " + listMyOrders);
			return ResponseEntity.status(HttpStatus.OK).body(new Response("OK", "", listMyOrders));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("FAILED", e.getMessage(), null));
		}
	}

}
