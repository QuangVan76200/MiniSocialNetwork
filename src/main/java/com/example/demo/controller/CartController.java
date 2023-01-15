package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.AddProductToCartRequest;
import com.example.demo.dto.request.CleanCartRequest;
import com.example.demo.dto.request.CreateShoppingCartRequest;
import com.example.demo.dto.request.RemoveFromCartDTO;
import com.example.demo.dto.respone.ResponseMessage;
import com.example.demo.entity.Response;
import com.example.demo.entity.ShoppingCart;
import com.example.demo.service.IShoppingCartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
	IShoppingCartService cartService;

	@PostMapping("/create_shopping_cart")
	public ResponseEntity<?> createShoppingCart(@RequestBody CreateShoppingCartRequest cartRequest) {
		try {
			ShoppingCart createCart = cartService.createShoppingCart(cartRequest);
			return ResponseEntity.status(HttpStatus.OK).body(new Response("OK", "Create sucessfully", createCart));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("FAILED", e.getMessage(), null));
		}
	}

	@PostMapping("/add_to_cart")
	public ResponseEntity<?> addToCart(@RequestBody AddProductToCartRequest productCart) {
		try {
			ShoppingCart addProductToCart = cartService.addProductToCart(productCart);
			return ResponseEntity.status(HttpStatus.OK).body(new Response("OK", "Add Successfull", addProductToCart));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("FAILED", e.getMessage(), null));
		}
	}

	@GetMapping("/get-all-cartItems/{cartId}")
	public ResponseEntity<?> getAllCartItems(@PathVariable("cartId") Long cartId) {
		try {
			ShoppingCart getAllCartItems = cartService.getAllCartItems(cartId);
			return ResponseEntity.status(HttpStatus.OK).body(new Response("OK", "Successfully", getAllCartItems));
		} catch (ResponseMessage e) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("FAILED", e.getMessage(), null));
		}

	}
	
	@PostMapping("/remove-from-cart")
	public ResponseEntity<?> removeFromCart(@RequestBody RemoveFromCartDTO request) {
		cartService.removeCartItemFromCart(request);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new Response("OK", "Delete successfully", request.getCartId()));
	}

	@PostMapping("/clean-cart")
	public ResponseEntity<?> cleanCart(@RequestBody CleanCartRequest cleanReques){
		try {
			cartService.cleanCart(cleanReques);
			return ResponseEntity.status(HttpStatus.OK).body(new Response("OK", "clean successfully from ",cleanReques.getCartId()));
		} catch (ResponseMessage e) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("FAILED",e.getMessage(), null));
		}
	}
}
