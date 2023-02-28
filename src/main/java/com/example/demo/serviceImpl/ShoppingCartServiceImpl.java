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

	/**
	 * 
	 * Add a product to a shopping cart.
	 * 
	 * @param productCart An object of type AddProductToCartRequest that includes
	 *                    the cart ID, the ID of the product to be added, and the
	 *                    quantity of the product.
	 * 
	 * @return The updated shopping cart object after the product has been added.
	 * 
	 * @throws ResponseMessage If the cart or the product cannot be found.
	 */
	@Override
	public ShoppingCart addProductToCart(AddProductToCartRequest productCart) throws ResponseMessage {

		final Long CART_ID = productCart.getCartId();
		final Long PRODUCT_ID = productCart.getProductItem().getProductId();
		final int QUANTITY = productCart.getQuantity();

		// FOUND PRODUCT IN CART
		CartItem presentCartItem = getCartItems(PRODUCT_ID, CART_ID);

		// FOUND HAVE THE CART
		ShoppingCart shoppingCart = shoppingCartDao.findById(CART_ID)
				.orElseThrow(() -> new ResponseMessage("Cart not found"));

		// FIND PRODUCT BY ID_PRODUCT
		Product product = productDao.findById(PRODUCT_ID).orElseThrow(() -> new ResponseMessage("Product not found"));

		Double PRICE_PER_UNIT = product.getPricePerUnit();

		Double totalPayment = 0D;

		// IF PRODUCT ALREADY EXIST IN CART. UPDATE QUANTITY AND TOATAL MONEY
		if (presentCartItem != null) {

			int totalQuantity = (presentCartItem.getQuantity() + QUANTITY);
			Double sumTotal = ((totalQuantity * PRICE_PER_UNIT));
			presentCartItem.setQuantity(totalQuantity);
			Double diffTotal = (presentCartItem.getTotalPrice());
			presentCartItem.setTotalPrice((presentCartItem.getTotalPrice() + sumTotal) - diffTotal);
			cartItemsDao.save(presentCartItem);

		} else {
			// CREATE NEW PRODUCT IN CART
			CartItem cartItem = new CartItem();
			cartItem.setProduct(product);
			cartItem.setShoppingCartId(shoppingCart);
			cartItem.setQuantity(QUANTITY);
			cartItem.setTotalPrice(PRICE_PER_UNIT * QUANTITY);
			System.out.println(cartItem.getTotalPrice());
			cartItemsDao.save(cartItem);
			shoppingCart.getListCartItem().add(cartItem);

		}

		for (CartItem items : shoppingCart.getListCartItem()) {
			totalPayment += items.getTotalPrice();
		}
		shoppingCart.setTotalPayment(totalPayment);

		return shoppingCartDao.save(shoppingCart);
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
			cartItemsDao.deleteByProductIdAndCartId(cleanRequest.getProductIds().get(i), cleanRequest.getCartId());
		}
		cartItems.get().setTotalPayment(0D);
		shoppingCartDao.save(cartItems.get());
	}

	@Override
	public CartItem getCartItems(Long productId, Long cartId) {
		return cartItemsDao.getCartItems(productId, cartId);
	}

}
