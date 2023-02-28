package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Product;

@Repository
public class IProductDaoQuery {

//	List<Product> search(String name, Double price, String description);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Product> search(String query) {
		List<Product> result = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Product.class));
		return result;
	}

}
