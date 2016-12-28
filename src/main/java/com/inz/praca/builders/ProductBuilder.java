package com.inz.praca.builders;

import java.math.BigDecimal;

import com.inz.praca.domain.Product;
import com.inz.praca.dto.ProductDTO;

public class ProductBuilder {
	private String name;
	private String description = "opis";
	private String imageUrl = "url";
	private BigDecimal price;

	public ProductBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public ProductBuilder withDescription(String description) {
		this.description = description;
		return this;
	}

	public ProductBuilder withUrl(String imageUrl) {
		this.imageUrl = imageUrl;
		return this;
	}

	public ProductBuilder withPrice(BigDecimal price) {
		this.price = price;
		return this;
	}

	public Product createProduct() {
		return new Product(name, description, imageUrl, price);
	}

	public ProductDTO createProductDTO() {
		return new ProductDTO(new Product(name, description, imageUrl, price));
	}

	public Product createProduct(ProductDTO productDTO) {
		return new Product(productDTO.getName(), productDTO.getDescription(), productDTO.getImageUrl(), productDTO.getPrice());
	}
}