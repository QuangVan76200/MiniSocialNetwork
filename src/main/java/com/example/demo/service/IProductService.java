package com.example.demo.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Product;

public interface IProductService {

	Product addProduct(MultipartFile imageFile, String name, String description, String brandName,
			Double pricePerUnit)throws IOException;

	List<Product> getAllProducts();
}
