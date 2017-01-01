package com.inz.praca.dto;

import javax.validation.constraints.Size;
import java.math.BigDecimal;

import com.inz.praca.domain.Product;
import com.inz.praca.validators.ValidPrice;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

@Data
@NoArgsConstructor
public class ProductDTO {

	@NotBlank //TODO moze wykorzystac notBlank do pokazania Chlebakowi
	@Size(min = 4, max = 15)
	private String name;

	private String imageUrl;

	private String description;

	@ValidPrice
	private BigDecimal price;

	@NotBlank
	private String category;

	public ProductDTO(Product product, String category) {
		this.name = product.getName();
		this.imageUrl = product.getImageUrl();
		this.description = product.getDescription();
		this.price = product.getPrice();
		this.category = category;
	}

	public ProductDTO(Product product) {
		this.name = product.getName();
		this.imageUrl = product.getImageUrl();
		this.description = product.getDescription();
		this.price = product.getPrice();
	}
}
