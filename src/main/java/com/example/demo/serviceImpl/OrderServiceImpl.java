package com.example.demo.serviceImpl;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ICartItemsDao;
import com.example.demo.dao.IOrderDetailsDao;
import com.example.demo.dao.IProductDao;
import com.example.demo.dao.IUserDao;
import com.example.demo.dto.request.CreateToOrderRequestDTO;
import com.example.demo.dto.request.GetAllMyOrdersDTO;
import com.example.demo.dto.request.OrderProductItem;
import com.example.demo.dto.respone.ResponseMessage;
import com.example.demo.entity.BillingAddress;
import com.example.demo.entity.CartItem;
import com.example.demo.entity.OrderDetails;
import com.example.demo.entity.Payment;
import com.example.demo.entity.Product;
import com.example.demo.entity.ShippingAddress;
import com.example.demo.entity.User;
import com.example.demo.service.IOrderService;
import com.example.demo.service.IUserService;

@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	IProductDao productDao;

	@Autowired
	HttpServletRequest request;

	@Autowired
	IUserService iUserService;

	@Autowired
	IUserDao userDao;

	@Autowired
	IOrderDetailsDao orderDetailDao;

	@Autowired
	ICartItemsDao cartItemDao;

//	@Override
//	public OrderDetails createOrder(CreateToOrderRequestDTO orderRequest) throws ResponseMessage {
//		try {
//			String user = request.getUserPrincipal().getName();
//			Optional<User> authUser = iUserService.getAuthenticatedUser(user);
//
//			Optional<User> currentUser = userDao.findById(orderRequest.getUserId());
//			OrderDetails myOrder = new OrderDetails();
//			if (currentUser.isPresent()) {
//				myOrder.setCustomerUser(authUser.get());
//			} else {
//				throw new ResponseMessage("Id user is not exists");
//			}
//			return orderDetailDao.save(myOrder);
//		} catch (Exception e) {
//			throw new ResponseMessage("Internal Server Error");
//		}
//
//	}

	/**
	 * 
	 * Creates a new order with the given parameters.
	 * 
	 * @param cartItemId      The ID of the cart item that will be used to create
	 *                        the order.
	 * 
	 * @param orderItem       The order item that will be created.
	 * 
	 * @param shippingAddress The shipping address for the order.
	 * 
	 * @param billingAddress  The billing address for the order.
	 * 
	 * @param payment         The payment for the order.
	 * 
	 * @param shippingMethod  The shipping method for the order.
	 * 
	 * @return The newly created order.
	 * 
	 * @throws ResponseMessage If there is an error creating the order.
	 */
	@Override
	@Transactional
	public OrderDetails createOrderItems(Long cartItemId, OrderProductItem orderItem, ShippingAddress shippingAddress,
			BillingAddress billingAddress, Payment payment, String shippingMethod) throws ResponseMessage {
		try {

			String user = request.getUserPrincipal().getName();
			Optional<User> authUser = iUserService.getAuthenticatedUser(user);

			Optional<CartItem> itemInCard = cartItemDao.findById(cartItemId);

			OrderDetails newOrder = new OrderDetails();

			if (itemInCard.isEmpty()) {
				throw new ResponseMessage("product doesn't have in Cart");
			} else {
				Optional<Product> findProduct = productDao.findById(itemInCard.get().getProduct().getProductId());

				if (findProduct.get().getProductQuantity() == 0) {
					throw new ResponseMessage("Out of stock");
				} else {

					newOrder.setBillingAddress(billingAddress);
					newOrder.setPayment(payment);
					newOrder.setShippingAddress(shippingAddress);

					newOrder.setCustomerUser(authUser.get());
					newOrder.setPaymentMethod(orderItem.getPaymentMethod().name());
					newOrder.setOrderStatus(orderItem.getStatus().name());
					newOrder.setTotalPrice(
							itemInCard.get().getProduct().getPricePerUnit() * itemInCard.get().getQuantity());
					newOrder.setOrderProduct(itemInCard.get().getProduct());
					newOrder.setOrderDay(LocalDateTime.now());
					shippingAddress.setOrder(newOrder);
					billingAddress.setOrder(newOrder);

					findProduct.get().setProductQuantity(findProduct.get().getProductQuantity() - 1);
				}
			}

//			Optional<Order> myOrder = orderDao.findById(orderRequest.getOrdersProductItems().get(0).getOrderId());
//			List<OrderDetails> orderDetails = new ArrayList<>();
//			for (int i = 0; i < orderRequest.getOrdersProductItems().size(); i++) {
//				Product productItems = productDao
//						.findById(orderRequest.getOrdersProductItems().get(i).getProductItem().getProductId())
//						  .orElseGet(() -> {
//					            throw new ResponseMessage("Product not found");
//					        });
//				OrderDetails orderItems = new OrderDetails();
//				
//				orderItems.setCustomerUser(authUser.get());
//				orderItems.setShippingAddress(orderRequest.getOrdersProductItems().get(i).getOrderAdress());
//				orderItems.setNumberPhone(orderRequest.getOrdersProductItems().get(i).getNumberPhone());
//				orderItems.set
//				orderItems.setPaymentMethod(orderRequest.getOrdersProductItems().get(i).getPaymentMethod().name());
//				orderItems.setTotalPrice(orderRequest.getOrdersProductItems().get(i).getTotalPrice());
//				orderItems.setOrderStatus(orderRequest.getOrdersProductItems().get(i).getStatus().name());
//				orderDetails.add(orderItems);
//
//			}

			return orderDetailDao.save(newOrder);

		} catch (Exception e) {
			throw new ResponseMessage("Internal Server Error");
		}
	}

	/**
	 * 
	 * This method cancels an order by setting its status to "Cancelled".
	 * 
	 * @param orderDetailsId The id of the order to be cancelled.
	 * @throws ResponseMessage If the order is not found.
	 */
	@Override
	@Transactional
	public void cancelOrder(Long orderDetailsId) throws ResponseMessage {

//		OrderDetails productOrder = orderDao.productOrders(productId, orderID);	
		Optional<OrderDetails> productOrder = orderDetailDao.findById(orderDetailsId);
		if (productOrder.isEmpty()) {
			throw new ResponseMessage("Order is not exists! Maybe wrong here");
		} else {

			productOrder.get().setOrderStatus("Cancelled");
			orderDetailDao.save(productOrder.get());

		}
	}

	/**
	 * 
	 * This method retrieves the details of an order.
	 * 
	 * @param listOrderRequest An object containing the id of the order to retrieve.
	 * @return The OrderDetails object corresponding to the given id.
	 * @throws ResponseMessage If the order is not found.
	 */
	@Override
	public OrderDetails getAllOrders(GetAllMyOrdersDTO listOrderRequest) throws ResponseMessage {
		Optional<OrderDetails> getAllOrders = orderDetailDao.findById(listOrderRequest.getOrderId());
		return getAllOrders.get();
	}

}
