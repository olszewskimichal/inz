package com.inz.praca.products;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

import com.inz.praca.validators.ValidPrice;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

@Data
@NoArgsConstructor
public class ProductDTO implements Serializable {

	@NotBlank
	@Size(min = 4, max = 15)
	private String name;

	private String imageUrl;

	private String description;

	@ValidPrice
	private BigDecimal price;

	@NotBlank
	private String category;

	private Long id;

	public ProductDTO(Product product, String category) {
		this.name = product.getName();
		this.imageUrl = product.getImageUrl();
		this.description = product.getDescription();
		this.price = product.getPrice();
		this.category = category;
		this.id = product.getId();
	}

	public ProductDTO(Product product) {
		this.name = product.getName();
		this.imageUrl = product.getImageUrl();
		this.description = product.getDescription();
		this.price = product.getPrice();
		this.id = product.getId();
	}
}
