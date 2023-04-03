package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ShoppingCart;
@Repository
public interface IShoppingCartDao extends JpaRepository<ShoppingCart, Long>{
	
	@Query(value = "select * from shopping_cart where user_id = :userId", nativeQuery = true)
	ShoppingCart getShoppingCartByUser(Long userId);

}
