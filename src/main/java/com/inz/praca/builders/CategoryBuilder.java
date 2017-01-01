package com.inz.praca.builders;

import com.inz.praca.domain.Category;

public class CategoryBuilder {
	private String name;
	private String description = "opis";

	public CategoryBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public CategoryBuilder withDescription(String description) {
		this.description = description;
		return this;
	}

	public Category createCategory() {
		return new Category(name, description);
	}

}