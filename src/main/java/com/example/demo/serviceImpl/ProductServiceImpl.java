package com.example.demo.serviceImpl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.IProductDao;
import com.example.demo.dto.respone.ResponseMessage;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.service.IProductService;
import com.example.demo.service.IUserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements IProductService {	

	@Autowired
	HttpServletRequest request;

	@Autowired
	IUserService iUserService;

	@Autowired
	IProductDao productDao;
	
	@Autowired
    StoreFile storeFile;
	
	@Override
	public Product addProduct(MultipartFile imageFile, String name, String description, String brandName,
			Double pricePerUnit) throws IOException {
		
			String user = request.getUserPrincipal().getName();
			Optional<User> authUser = iUserService.getAuthenticatedUser(user);
			Product newProduct = new Product();
			newProduct.setUser(authUser.get());
			newProduct.setName(name);
			newProduct.setDescription(description);
			newProduct.setBrandName(brandName);
			newProduct.setPricePerUnit(pricePerUnit);
//			String imageUrl = this.amazonClient.uploadFile(imageFile);
//			newProduct.setImageUrl(imageUrl);
	        if (imageFile != null && imageFile.getSize() > 0) {
	            String imgUrl = storeFile.uploadFile(imageFile).toString();
	            newProduct.setImageUrl(imgUrl);
	        } else {
	            new ResponseMessage("imageUrl is empty");
	        }

			return productDao.save(newProduct);

		
	}

	@Override
	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return productDao.findAll();
	}

}
