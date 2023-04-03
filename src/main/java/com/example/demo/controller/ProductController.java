package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.respone.ProductDTO;
import com.example.demo.entity.Product;
import com.example.demo.entity.Response;
import com.example.demo.service.IProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {

	@Autowired
	IProductService productService;

	@PostMapping("/addProduct")
	public ResponseEntity<?> addProduct(@RequestParam("imageFile") MultipartFile imageFile,
			@RequestParam("name") String name, @RequestParam("description") String description,
			@RequestParam("brandName") String brandName, @RequestParam("pricePerUnit") Double pricePerUnit) {
		try {
			System.out.println("cicic");
			ProductDTO newProduct = productService.addProduct(imageFile, name, description, brandName, pricePerUnit);

			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response("OK", "Insert newpost sucessfully", newProduct));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Failed", e.getMessage(), null));
		}

	}

	@GetMapping("/all-products")
	public ResponseEntity<?> getAllProducts() {
		List<Product> listProduct = productService.getAllProducts();
		return ResponseEntity.status(HttpStatus.OK).body(new Response("OK", "", listProduct));
	}

	@GetMapping("/search-product")
	public ResponseEntity<?> searchProduct(@RequestParam(required = false) String name,
			@RequestParam(required = false) Double pricePerUnit, @RequestParam(required = false) String brandName) {
		List<Product> listProduct = productService.search(name, pricePerUnit, brandName);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("OK", "", listProduct));
	}

}
