package com.example.demo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.respone.ProductDTO;
import com.example.demo.entity.Product;

public interface IProductService {

	ProductDTO addProduct(MultipartFile imageFile, String name, String description, String brandName,
			Double pricePerUnit)throws Exception;

	List<Product> getAllProducts();
	
	List<Product> search(String name, Double pricePerUnit, String brandName);
}
