package com.inz.praca.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.util.Assert;

@ToString
@Getter
@Setter
public class CartItemDTO implements Serializable {
	private ProductDTO item;

	private Integer quantity;

	private BigDecimal price = BigDecimal.ZERO;

	public CartItemDTO(ProductDTO item) {
		Assert.notNull(item);
		this.item = item;
		this.quantity = 1;
		this.price = item.getPrice();
	}

}
