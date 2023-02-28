package com.example.demo.serviceImpl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.IProductDao;
import com.example.demo.dao.IProductDaoQuery;
import com.example.demo.dto.respone.ResponseMessage;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.service.IProductService;
import com.example.demo.service.IUserService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

	@Autowired
	HttpServletRequest request;

	@Autowired
	IUserService iUserService;

	@Autowired
	IProductDao productDao;

	@Autowired
	StoreFile storeFile;

	@Autowired
	EntityManager entityManager;

	@Autowired
	private IProductDaoQuery productDaoQuery;

	/**
	 * 
	 * Add a new product with given parameters
	 * 
	 * @param imageFile    multipart file of the image to be uploaded
	 * 
	 * @param name         name of the product
	 * 
	 * @param description  description of the product
	 * 
	 * @param brandName    brand name of the product
	 * 
	 * @param pricePerUnit price per unit of the product
	 * 
	 * @return the saved Product object with the provided parameters
	 * 
	 * @throws Exception if imageFile is empty or the upload to the server fails
	 */
	@Override
	public Product addProduct(MultipartFile imageFile, String name, String description, String brandName,
			Double pricePerUnit) throws Exception {

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
			throw new ResponseMessage("imageUrl is empty");
		}

		return productDao.save(newProduct);

	}

	@Override
	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return productDao.findAll();
	}

	/**
	 * 
	 * Search for products based on the given parameters.
	 * 
	 * @param name         the name of the product, can be null or empty to ignore
	 * @param pricePerUnit the price per unit of the product, can be null or 0 to
	 *                     ignore
	 * @param brandName    the brand name of the product, can be null or empty to
	 *                     ignore
	 * @return a list of products that match the search criteria
	 */
	@Override
	public List<Product> search(String name, Double pricePerUnit, String brandName) {
		StringBuilder queryBuilder = new StringBuilder("SELECT * FROM your_table WHERE ");
		boolean hasParameter = false;
		if (name != null && !name.isEmpty()) {
			queryBuilder.append("name LIKE '%" + name + "%' ");
			hasParameter = true;
		}
		if (pricePerUnit != null && !(pricePerUnit == 0)) {
			if (hasParameter) {
				queryBuilder.append("AND ");
			}
			queryBuilder.append("pricePerUnit LIKE '%" + pricePerUnit + "%' ");
			hasParameter = true;
		}
		if (brandName != null && !brandName.isEmpty()) {
			if (hasParameter) {
				queryBuilder.append("AND ");
			}
			queryBuilder.append("brandName LIKE '%" + brandName + "%' ");
		}

		List<Product> searchResult = productDaoQuery.search(queryBuilder.toString());
		return searchResult;
	}

}
