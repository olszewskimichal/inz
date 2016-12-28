package com.inz.praca.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import org.springframework.util.Assert;

@Entity
@ToString
@Setter
@Slf4j
@Getter
public class Category {
	private final String name;
	private final String description;

	@Id
	@GeneratedValue
	private Long id;

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	private Set<Product> products;

	public Category(String name, String description) {
		Assert.hasLength(name, "Nie moze być pusta nazwa kategorii");
		this.name = name;
		this.description = description;
	}

	private Category() {
		this.name = null;
		this.description = null;
	}

	public void setId(long id) {
		this.id = id;
	}
}
