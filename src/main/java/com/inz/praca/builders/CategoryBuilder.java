package com.inz.praca.builders;

import com.inz.praca.domain.Category;
import com.inz.praca.dto.CategoryDTO;

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

	public Category createCategory(CategoryDTO categoryDTO) {
		return new Category(categoryDTO.getName(), categoryDTO.getDescription());
	}
}