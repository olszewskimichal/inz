package com.inz.praca.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.util.Assert;

@Entity
@ToString
@Getter
@Setter
public class CartItem {

	@Id
	@GeneratedValue
	private Long id;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "PRODUCT_ID")
	private final Product product;

	private final Long quantity;

	public CartItem(Product product, Long quantity) {
		Assert.notNull(quantity, "Ilosc produktów nie może być nullem");
		Assert.notNull(product, "Produkt nie moze być nullem");
		Assert.isTrue(quantity.compareTo(0L) >= 0, "Ilosc produktów musi byc wieksza badz rowna 0");
		this.product = product;
		this.quantity = quantity;
	}

	private CartItem() {
		this.quantity = null;
		product = null;
	}
}