package com.inz.praca.category;

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