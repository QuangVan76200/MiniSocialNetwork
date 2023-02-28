package com.example.demo.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.CartItem;

@Repository
public interface ICartItemsDao extends JpaRepository<CartItem, Long> {

	@Query(value = "select \r\n"
			+ "	 *\r\n"
			+ "from product p inner join cart_item c on p.product_id = c.product_id inner join shopping_cart sc on c.cart_id = sc.cart_id  where c.product_id=:productId and  sc.cart_id=:cartId\r\n"
			+ "", nativeQuery = true)
	CartItem getCartItems(Long productId, Long cartId);
	
	@Transactional
	@Modifying
	@Query(value = "delete from cart_item where product_id = :productId and cart_id = :cartId", nativeQuery = true)
	void deleteByProductIdAndCartId(Long productId, Long cartId);
	
	
	

}
