package com.inz.praca.integration.domain;

import static org.assertj.core.api.Java6Assertions.assertThat;

import com.inz.praca.domain.Category;
import com.inz.praca.integration.JpaTestBase;
import org.junit.Test;

public class CategoryEntityTest extends JpaTestBase {

	@Test
	public void shouldPersistCategoryWhenObjectIsCorrect() {
		Category category = entityManager.persistFlushFind(new Category("nazwa", "opis"));
		assertThat(category.getName()).isEqualTo("nazwa");
		assertThat(category.getDescription()).isEqualTo("opis");
		assertThat(category.getId()).isNotNull();
		assertThat(category.toString()).contains("nazwa");
	}
}
