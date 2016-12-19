package com.inz.praca.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import org.springframework.util.Assert;

@Entity
@ToString
@Getter
@Slf4j
public class Product {
	private final String name;
	private final String description;
	private final BigDecimal price;
	private final String imageUrl;
	@Id
	@GeneratedValue
	private Long id;

	protected Product() {
		this.name = null;
		this.description = null;
		this.price = null;
		this.imageUrl = null;
		log.debug("Pusty konstuktor dla hibernate, chce wiedzieć kiedy jest wykonywany {}", this);
	}

	public Product(String name, String description, String imageUrl, BigDecimal price) {
		Assert.hasLength(name, "Nie moze być pusta nazwa produktu");
		Assert.notNull(price, "Cena nie moze byc nullem");
		Assert.isTrue(price.compareTo(BigDecimal.ZERO) >= 0, "Cena nie moze być mniejsza od 0");
		this.name = name;
		this.description = description;
		this.imageUrl = imageUrl;
		this.price = price;
	}

	public void setId(long id) {
		this.id = id;
	}
}
