package com.example.demo.serviceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import com.example.demo.dto.respone.CartItemDTO;
import com.example.demo.dto.respone.ResponseMessage;
import com.example.demo.dto.respone.ShoppingCartDTO;
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

	@Autowired
	IShoppingCartDao cartDao;

	@Override
	public ShoppingCartDTO createShoppingCart(CreateShoppingCartRequest cartRequest) throws ResponseMessage {
		try {
			String user = request.getUserPrincipal().getName();
			Optional<User> authUser = iUserService.getAuthenticatedUser(user);

			Optional<User> currentUser = userDao.findById(cartRequest.getUserId());

			if (!currentUser.isPresent()) {
				throw new ResponseMessage("User ID not found");
			}
			// Check if user already has a shopping cart
			ShoppingCart existingShoppingCart = cartDao.getShoppingCartByUser(currentUser.get().getId());

			if (existingShoppingCart != null) {
				ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
				shoppingCartDTO.setShoppingCartId(existingShoppingCart.getCartId());
				shoppingCartDTO.setUserId(existingShoppingCart.getUser().getId());
				return shoppingCartDTO;
			}
			// Create new shopping cart
			ShoppingCart newShoppingCart = new ShoppingCart();
			newShoppingCart.setUser(authUser.get());
			ShoppingCart savedShoppingCart = shoppingCartDao.save(newShoppingCart);

			ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
			shoppingCartDTO.setShoppingCartId(savedShoppingCart.getCartId());
			shoppingCartDTO.setUserId(savedShoppingCart.getUser().getId());
			return shoppingCartDTO;

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
	public ShoppingCartDTO addProductToCart(AddProductToCartRequest productCart) throws ResponseMessage {

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
			cartItemsDao.save(cartItem);
			shoppingCart.getListCartItem().add(cartItem);

		}

		for (CartItem items : shoppingCart.getListCartItem()) {
			totalPayment += items.getTotalPrice();
		}
		shoppingCart.setTotalPayment(totalPayment);

		ShoppingCart savedShoppingCart = shoppingCartDao.save(shoppingCart);
		return ShoppingCartDTO.fromEntity(savedShoppingCart);
	}

	@Override
	@Transactional
	public void removeCartItemFromCart(RemoveFromCartDTO request) {
		cartItemsDao.deleteByProductIdAndCartId(request.getProductId(), request.getCartId());
	}

	@Override
	public ShoppingCartDTO getAllCartItems(Long cartId) throws ResponseMessage {
		Optional<ShoppingCart> cartOptional = shoppingCartDao.findById(cartId);
		ShoppingCart cart = cartOptional.orElseThrow(() -> new ResponseMessage("Cart not found"));

		Set<CartItem> cartItems = cart.getListCartItem();
		List<CartItemDTO> cartItemDTOs = new ArrayList<>(cartItems.size());

		for (CartItem cartItem : cartItems) {
			CartItemDTO cartItemDTO = CartItemDTO.fromEntity(cartItem);
			cartItemDTOs.add(cartItemDTO);
		}

		ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
		shoppingCartDTO.setShoppingCartId(cart.getCartId());
		shoppingCartDTO.setUserId(cart.getUser().getId());
		shoppingCartDTO.setCartItemDTO(cartItemDTOs);
		shoppingCartDTO.setTotalPayment(cart.getTotalPayment());

		return shoppingCartDTO;
	}

	@Override
	public void cleanCart(CleanCartRequest cleanRequest) throws ResponseMessage {
		Optional<ShoppingCart> optionalCart = shoppingCartDao.findById(cleanRequest.getCartId());
		if (optionalCart.isEmpty()) {
			throw new ResponseMessage("Cart not found");
		}

		ShoppingCart cart = optionalCart.get();
		Set<Long> productIds = new HashSet<>(cleanRequest.getProductIds());
		cart.getListCartItem().removeIf(item -> productIds.contains(item.getProduct().getProductId()));

		double totalPayment = cart.getListCartItem().stream().mapToDouble(CartItem::getTotalPrice).sum();

		cart.setTotalPayment(totalPayment);
		shoppingCartDao.save(cart);
	}

	@Override
	public CartItem getCartItems(Long productId, Long cartId) {
		return cartItemsDao.getCartItems(productId, cartId);
	}

}
