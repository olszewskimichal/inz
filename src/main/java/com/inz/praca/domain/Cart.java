package com.inz.praca.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.inz.praca.builders.ProductBuilder;
import com.inz.praca.dto.CartItemDTO;
import com.inz.praca.dto.CartSession;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import org.springframework.util.Assert;

@Entity
@Getter
@ToString
@Setter
@Slf4j
public class Cart {
	@Id
	@GeneratedValue
	private Long id;

	@ManyToMany(targetEntity = CartItem.class, cascade = CascadeType.ALL)
	@JoinTable(name = "CART_PRODUCTS",
			joinColumns = @JoinColumn(name = "CART_ID"),
			inverseJoinColumns = @JoinColumn(name = "CART_ITEM_ID"))
	private Set<CartItem> cartItems=new HashSet<>();
	private LocalDateTime dateTime;

	public Cart(Set<CartItem> cartItems) {
		Assert.notNull(cartItems, "Lista elementów koszyka nie może być nullem");
		this.cartItems = cartItems;
	}

	public Cart(CartSession cartSession) {
		Assert.notNull(cartSession, "Nieprawidłowy koszyk");
		Assert.notEmpty(cartSession.getItems(), "Koszyk musi zawierac jakies produkty");
		for (CartItemDTO cartItemDTO : cartSession.getItems()) {
			CartItem cartItem = new CartItem(new ProductBuilder().createProduct(cartItemDTO.getItem()), cartItemDTO.getQuantity().longValue());
			cartItems.add(cartItem);
		}

	}

	private Cart() {

	}

	@PrePersist
	public void initialize() {
		this.dateTime = LocalDateTime.now();
	}
}
