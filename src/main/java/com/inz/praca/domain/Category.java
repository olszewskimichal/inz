package com.inz.praca.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import org.springframework.util.Assert;

@Entity
@ToString
@Setter
@Slf4j
@Getter
@NoArgsConstructor(force = true)
public class Category {
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

	public void setId(long id) {
		this.id = id;
	}
}
