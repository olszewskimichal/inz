package com.inz.praca.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
@ToString
public class ProductDTO {

	@NotBlank //TODO moze wykorzystac notBlank do pokazania Chlebakowi
	@Size(min = 4, max = 15)
	private String name;

	private String imageUrl;

	private String description;

	@NotNull
	//@Pattern(regexp = "^\\d{0,9}(\\.\\d{1,9})?$")
	//TODO napisac validator do tego
	@DecimalMin(value = "0")
	private BigDecimal price;
}
