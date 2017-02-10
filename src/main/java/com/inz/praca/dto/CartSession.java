package com.inz.praca.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.inz.praca.validators.ValidPrice;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@ToString
@Component
@SessionScope
@Getter
@NoArgsConstructor
public class CartSession implements Serializable {
	@NotNull
	private List<CartItemDTO> items = new ArrayList<>();
	@ValidPrice
	private BigDecimal totalPrice = BigDecimal.ZERO;

	public CartSession(CartSession cartSession) {
		this.items = cartSession.getItems();
		this.totalPrice = cartSession.getTotalPrice();
	}

	public void addProductDTO(ProductDTO productDTO) {
		for (CartItemDTO cartItemDTO : items) {
			if (cartItemDTO.getItem().equals(productDTO)) {
				cartItemDTO.setQuantity(cartItemDTO.getQuantity() + 1);
				cartItemDTO.setPrice(cartItemDTO.getItem().getPrice().multiply(BigDecimal.valueOf(cartItemDTO.getQuantity())));
				updatePrice();
				return;
			}
		}
		items.add(new CartItemDTO(productDTO));
		updatePrice();
	}

	public void removeProductDTO(int rowId) {
		if (!items.isEmpty()) {
			items.remove(rowId);
			updatePrice();
		}
	}

	public void clearCart() {
		items = new ArrayList<>();
		updatePrice();
	}

	private void updatePrice() {
		totalPrice = items.stream().map(CartItemDTO::getPrice).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
	}

	public List<CartItemDTO> getItems() {
		if (items == null)
			items = new ArrayList<>();
		return items;
	}
}
