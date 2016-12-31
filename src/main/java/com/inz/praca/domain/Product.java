package com.inz.praca.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import org.springframework.util.Assert;

@Entity
@ToString
@Getter
@Slf4j
@NoArgsConstructor(force = true)
public class Product {
	private final String name;
	private final String description;
	private final BigDecimal price;
	private final String imageUrl;
	@ManyToOne(cascade = CascadeType.ALL)
	//@JoinColumn(name = "CATEGORY_ID")
	public Category category;
	@Id
	@GeneratedValue
	private Long id;

	public Product(String name, String description, String imageUrl, BigDecimal price) {
		Assert.hasLength(name, "Nie moze być pusta nazwa produktu");
		Assert.notNull(price, "Cena nie moze byc nullem");
		Assert.isTrue(price.compareTo(BigDecimal.ZERO) >= 0, "Cena nie moze być mniejsza od 0");
		this.name = name;
		this.description = description;
		this.imageUrl = imageUrl;
		this.price = price;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
