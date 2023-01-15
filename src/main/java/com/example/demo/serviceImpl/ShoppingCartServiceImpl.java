package com.example.demo.serviceImpl;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ICartItemsDao;
import com.example.demo.dao.IProductDao;
import com.example.demo.dao.IShoppingCartDao;
import com.example.demo.dao.IUserDao;
import com.example.demo.dto.request.AddProductToCartRequest;
import com.example.demo.dto.request.CleanCartRequest;
import com.example.demo.dto.request.CreateShoppingCartRequest;
import com.example.demo.dto.request.RemoveFromCartDTO;
import com.example.demo.dto.respone.ResponseMessage;
import com.example.demo.entity.CartItem;
import com.example.demo.entity.Product;
import com.example.demo.entity.ShoppingCart;
import com.example.demo.entity.User;
import com.example.demo.service.IShoppingCartService;
import com.example.demo.service.IUserService;

@Service
public class ShoppingCartServiceImpl implements IShoppingCartService {

	@Autowired
	HttpServletRequest request;

	@Autowired
	IUserService iUserService;

	@Autowired
	IUserDao userDao;

	@Autowired
	IShoppingCartDao shoppingCartDao;

	@Autowired
	ICartItemsDao cartItemsDao;

	@Autowired
	IProductDao productDao;

	@Override
	public ShoppingCart createShoppingCart(CreateShoppingCartRequest cartRequest) throws ResponseMessage {
		try {
			String user = request.getUserPrincipal().getName();
			Optional<User> authUser = iUserService.getAuthenticatedUser(user);

			Optional<User> currentUser = userDao.findById(cartRequest.getUserId());
			ShoppingCart newShoppingCart = new ShoppingCart();
			if (currentUser.isPresent()) {

				newShoppingCart.setUser(authUser.get());

			} else {
				throw new ResponseMessage("Id user is not exists");
			}

			return shoppingCartDao.save(newShoppingCart);
		} catch (Exception e) {
			throw new ResponseMessage("Internal Server Error");
		}
	}

	@Override
	public ShoppingCart addProductToCart(AddProductToCartRequest productCart) throws ResponseMessage {

		Double totalPayment = 0D;

		CartItem presentCartItem = getCartItems(productCart.getProductItem().getProductId(), productCart.getCartId());

		Optional<ShoppingCart> cart = shoppingCartDao.findById(productCart.getCartId());
		Optional<Product> productItems = productDao.findById(productCart.getProductItem().getProductId());

		if (presentCartItem != null) {
			Double totalQuantity = (presentCartItem.getQuantity() + productCart.getQuantity());
			Double sumTotal = ((totalQuantity * productItems.get().getPricePerUnit()));
			presentCartItem.setQuantity(totalQuantity);
			Double diffTotal = (presentCartItem.getTotalPrice());
			presentCartItem.setTotalPrice((presentCartItem.getTotalPrice() + sumTotal) - diffTotal);
			cartItemsDao.save(presentCartItem);

		} else {
			CartItem cartItem = new CartItem();

			cartItem.setProduct(productItems.get());
			cartItem.setCartId(cart.get());
			cartItem.setQuantity((double) productCart.getQuantity());
			cartItem.setTotalPrice(productItems.get().getPricePerUnit());
			cartItemsDao.save(cartItem);

		}
		for (CartItem items : cart.get().getListCartItem()) {
			totalPayment += items.getTotalPrice();
		}
		cart.get().setTotalPayment(totalPayment);

		return shoppingCartDao.save(cart.get());
	}

	@Override
	@Transactional
	public void removeCartItemFromCart(RemoveFromCartDTO request) {
		cartItemsDao.deleteByProductIdAndCartId(request.getProductId(), request.getCartId());
	}

	@Override
	public ShoppingCart getAllCartItems(Long cartId) throws ResponseMessage {
		Optional<ShoppingCart> cartItems = shoppingCartDao.findById(cartId);
		if (cartItems.isEmpty()) {
			throw new ResponseMessage("Cart not Found");
		}
		return cartItems.get();
	}

	@Override
	public void cleanCart(CleanCartRequest cleanRequest) throws ResponseMessage {
		Optional<ShoppingCart> cartItems = shoppingCartDao.findById(cleanRequest.getCartId());
		if (cartItems.isEmpty()) {
			throw new ResponseMessage("Cart not Found");
		}
		int size = cleanRequest.getProductIds().size();
		for (int i = 0; i < size; i++) {
			cartItemsDao.deleteByProductIdAndCartId(cleanRequest.getProductIds().get(i), 
					cleanRequest.getCartId());
		}
		cartItems.get().setTotalPayment(0D);
		shoppingCartDao.save(cartItems.get());
	}

	@Override
	public CartItem getCartItems(Long productId, Long cartId) {
		return cartItemsDao.getCartItems(productId, cartId);
	}

}
