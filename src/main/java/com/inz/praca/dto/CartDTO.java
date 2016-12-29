package com.inz.praca.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.inz.praca.validators.ValidPrice;
import lombok.Getter;
import lombok.ToString;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@ToString
@Getter
@Component
@SessionScope
public class CartDTO implements Serializable {
	@NotNull
	transient List<ProductDTO> items;
	@ValidPrice
	private BigDecimal totalPrice = BigDecimal.ZERO;

	public void addProductDTO(ProductDTO productDTO) {
		if (items == null)
			items = new ArrayList<>();
		items.add(productDTO);
		updatePrice();
	}

	public void removeProductDTO(int rowId) {
		if (items == null)
			items = new ArrayList<>();
		if (!items.isEmpty()) {
			items.remove(rowId);
			updatePrice();
		}
	}

	private void updatePrice() {
		totalPrice = items.stream().map(ProductDTO::getPrice).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
	}
}
