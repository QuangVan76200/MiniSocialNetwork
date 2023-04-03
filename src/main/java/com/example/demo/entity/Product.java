package com.example.demo.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Product")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;

	@Column(name = "title")
	private String name;

	@Column(columnDefinition = "Text")
	private String description;

	@Column(name = "brandName")
	private String brandName;

	@Column(name = "productQuantity")
	private int productQuantity;

	@Column(name = "pricePerUnit")
	private Double pricePerUnit;

	@Column(name = "imageUrl")
	private String imageUrl;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "product",  orphanRemoval = true, fetch = FetchType.LAZY)
	private List<CartItem> listCartItems;

	@ManyToOne
	@JoinColumn(name = "userId", nullable = false, referencedColumnName = "userId")
	private User user;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "productImage")
	Set<ImageFile> images;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	public Double getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(Double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<CartItem> getListCartItems() {
		return listCartItems;
	}

	public void setListCartItems(List<CartItem> listCartItems) {
		this.listCartItems = listCartItems;
	}

//	public User getUser() {
//		return user.getFullName;
//	}

	public void setUser(User user) {
		this.user = user;
	}

	// @Override
	// public String toString() {
	// return "Product [productId=" + productId + ", name=" + name + ",
	// description=" + description + ", brandName="
	// + brandName + ", productQuantity=" + productQuantity + ", pricePerUnit=" +
	// pricePerUnit + ", imageUrl="
	// + imageUrl + ", listOrderDetails=" + listOrderDetails + ", listCartItems=" +
	// listCartItems + ", user="
	// + user + "]";
	// }

}
