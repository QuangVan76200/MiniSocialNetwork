package com.example.demo.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.IOrderDao;
import com.example.demo.dao.IOrderDetailsDao;
import com.example.demo.dao.IProductDao;
import com.example.demo.dao.IUserDao;
import com.example.demo.dto.request.CreateOrderRequest;
import com.example.demo.dto.request.CreateToOrderRequestDTO;
import com.example.demo.dto.request.OrderProductItem;
import com.example.demo.dto.respone.ResponseMessage;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetails;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.service.IOrderService;
import com.example.demo.service.IUserService;

@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	IOrderDao orderDao;

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

	@Override
	public Order createOrder(CreateToOrderRequestDTO orderRequest) throws ResponseMessage {
		try {
			String user = request.getUserPrincipal().getName();
			Optional<User> authUser = iUserService.getAuthenticatedUser(user);

			Optional<User> currentUser = userDao.findById(orderRequest.getUserId());
			Order myOrder = new Order();
			if (currentUser.isPresent()) {
				myOrder.setUser(authUser.get());
			} else {
				throw new ResponseMessage("Id user is not exists");
			}
			return orderDao.save(myOrder);
		} catch (Exception e) {
			throw new ResponseMessage("Internal Server Error");
		}

	}

	@Override
	public Order createOrderItems(CreateOrderRequest orderRequest) throws ResponseMessage {
		try {

			String user = request.getUserPrincipal().getName();
			Optional<User> authUser = iUserService.getAuthenticatedUser(user);

			Optional<Order> myOrder = orderDao.findById(orderRequest.getOrdersProductItems().get(0).getOrderId());
			List<OrderDetails> orderDetails = new ArrayList<>();
			for (int i = 0; i < orderRequest.getOrdersProductItems().size(); i++) {
				Optional<Product> productItems = productDao
						.findById(orderRequest.getOrdersProductItems().get(i).getProductItem().getProductId());
				OrderDetails orderItems = new OrderDetails();
				orderItems.setProduct(productItems.get());
				orderItems.setProductOrder(myOrder.get());
				orderItems.setQuantity(orderRequest.getOrdersProductItems().get(i).getQuantity());
				orderItems.setTotalPrice((double) (orderRequest.getOrdersProductItems().get(i).getQuantity()
						* productItems.get().getPricePerUnit()));
				orderItems.setOrderStatus("Inprogress");
				orderDetails.add(orderItems);

			}
			myOrder.get().setOrderItems(orderDetails);
			myOrder.get().setNumberPhone(orderRequest.getOrdersProductItems().get(0).getNumberPhone());
			myOrder.get().setOrderAdress(orderRequest.getOrdersProductItems().get(0).getOrderAdress());
			myOrder.get().setCreatedAt(new Date());

			myOrder.get().setUser(authUser.get());
			return orderDao.save(myOrder.get());

		} catch (Exception e) {
			throw new ResponseMessage("Internal Server Error");
		}

	}

	@Override
	public void cancelOrder(Long productId, Long orderID) throws ResponseMessage {

		OrderDetails productOrder = orderDao.productOrders(productId, orderID);
		if (productOrder == null) {
			throw new ResponseMessage("Order is not exists! Maybe wrong here");
		} else {

			productOrder.setOrderStatus("Cancelled");
			orderDetailDao.save(productOrder);

		}
	}

	@Override
	public List<Order> getAllOrders() {
		// TODO Auto-generated method stub
		return null;
	}

}
