package com.example.demo.service;

import com.example.demo.dto.request.AddProductToCartRequest;
import com.example.demo.dto.request.CleanCartRequest;
import com.example.demo.dto.request.CreateShoppingCartRequest;
import com.example.demo.dto.request.RemoveFromCartDTO;
import com.example.demo.dto.respone.ResponseMessage;
import com.example.demo.entity.CartItem;
import com.example.demo.entity.ShoppingCart;

public interface IShoppingCartService {
	
	ShoppingCart createShoppingCart(CreateShoppingCartRequest request) throws ResponseMessage;

	ShoppingCart addProductToCart(AddProductToCartRequest request)throws ResponseMessage;

	void removeCartItemFromCart(RemoveFromCartDTO request);
	
	ShoppingCart getAllCartItems(Long cartId) throws ResponseMessage;
	
	void cleanCart(CleanCartRequest cleanRequest) throws ResponseMessage;
	
	CartItem getCartItems(Long productId, Long cartId);

}
