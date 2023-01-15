package com.example.demo.dao;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.OrderDetails;

@Repository
public interface IOrderDetailsDao extends JpaRepository<OrderDetails, Long	> {
	
	@Transactional
	@Modifying
	@Query(value = "select * from order_details where product_id = :productId and order_Id = :orderId", nativeQuery = true)
	void findByByProductIdAndOrderId(Long productId, Long orderId);
	
	@Query(value = "select * from product p inner join order_details od on p.product_id= od.product_id inner join orders o on od.order_id =o.order_id\r\n"
			+ "where c.product_id = :productId AND o.order_id= :orderId", nativeQuery = true)
	OrderDetails productOrders(Long productId, Long orderId);

}
	