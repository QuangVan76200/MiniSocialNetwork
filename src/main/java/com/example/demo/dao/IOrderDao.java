package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetails;
@Repository
public interface IOrderDao extends JpaRepository<Order, Long>{
	
	@Query(value = "select * from product p inner join order_details od on p.product_id= od.product_id inner join orders o on od.order_id =o.order_id\r\n"
			+ "where c.product_id = :productId AND o.order_id= :orderId", nativeQuery = true)
	OrderDetails productOrders(Long productId, Long orderId);

}
