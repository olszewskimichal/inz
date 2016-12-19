package com.inz.praca.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductDTO {

	@Size(min = 4, max = 15)
	private String name;

	private String imageUrl;

	private String description;

	//@NotNull
	//@Pattern(regexp = "^\\d{0,9}(\\.\\d{1,9})?$")
	//TODO napisac validator do tego
	@DecimalMin(value = "0")
	private BigDecimal price;
}
