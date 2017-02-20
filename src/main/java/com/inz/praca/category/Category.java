package com.inz.praca.category;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import org.springframework.util.Assert;

@Entity
@Setter
@Slf4j
@Getter
@ToString
public class Category {
	@Column(unique = true)
	private final String name;
	private final String description;

	@Id
	@GeneratedValue
	private Long id;

	public Category(String name, String description) {
		Assert.hasLength(name, "Nie moze być pusta nazwa kategorii");
		this.name = name;
		this.description = description;
	}

	private Category() {
		this.name = null;
		this.description = null;
	}
}
