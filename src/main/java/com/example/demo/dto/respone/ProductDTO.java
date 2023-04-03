package com.example.demo.dto.respone;

public class ProductDTO {

	private Long id;

	private String name;

	private String description;

	private String brandName;

	private Double pricePerUnit;

	private String imageUrl;

	public ProductDTO() {
	}

	public ProductDTO(Long id, String name, String description, String brandName, Double pricePerUnit,
			String imageUrl) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.brandName = brandName;
		this.pricePerUnit = pricePerUnit;
		this.imageUrl = imageUrl;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
}
