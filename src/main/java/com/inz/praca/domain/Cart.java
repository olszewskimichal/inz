package com.inz.praca.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import org.springframework.util.Assert;

@Entity
@Getter
@ToString
@Setter
@Slf4j
@NoArgsConstructor(force = true)
public class Cart {
	@Id
	@GeneratedValue
	private Long id;

	@ManyToMany(targetEntity = Product.class, cascade = CascadeType.ALL)
	@JoinTable(name = "CART_PRODUCTS",
			joinColumns = @JoinColumn(name = "cart_id"),
			inverseJoinColumns = @JoinColumn(name = "product_id"))
	private List<Product> products;
	private final LocalDateTime dateTime;

	public Cart(List<Product> products, LocalDateTime dateTime) {
		Assert.notNull(products, "Lista produktów nie może być nullem");
		this.products = products;
		this.dateTime = dateTime;
	}
}
