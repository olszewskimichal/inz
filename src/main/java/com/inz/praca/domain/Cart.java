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
import java.util.Set;

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

	//TODO transient price

	@ManyToMany(targetEntity = CartItem.class, cascade = CascadeType.ALL)
	@JoinTable(name = "CART_PRODUCTS",
			joinColumns = @JoinColumn(name = "CART_ID"),
			inverseJoinColumns = @JoinColumn(name = "CART_ITEM_ID"))
	private Set<CartItem> cartItems;
	private LocalDateTime dateTime;

	public Cart(Set<CartItem> cartItems) {
		Assert.notNull(cartItems, "Lista elementów koszyka nie może być nullem");
		this.cartItems = cartItems;
	}

	private Cart() {

	}

	@PrePersist
	public void initialize() {
		this.dateTime = LocalDateTime.now();
	}
}
