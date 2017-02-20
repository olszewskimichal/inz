package com.inz.praca.products;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

import com.inz.praca.category.Category;
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
	@Column(unique = true)
	private final String name;
	private final String description;
	private final BigDecimal price;
	private final String imageUrl;

	@Column(columnDefinition = "tinyint(1) default 1")
	private Boolean active;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "CATEGORY_ID")
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
		this.active = true;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}
