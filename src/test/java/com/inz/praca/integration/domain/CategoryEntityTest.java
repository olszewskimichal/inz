package com.inz.praca.integration.domain;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.PersistenceException;

import com.inz.praca.category.Category;
import com.inz.praca.integration.JpaTestBase;
import org.junit.Assert;
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

	@Test
	public void shouldNotPersistWithNotUniqueName() {
		try {
			entityManager.persistFlushFind(new Category("nazwa", "opis"));
			entityManager.persistFlushFind(new Category("nazwa", "opis"));
			Assert.fail();
		}
		catch (PersistenceException e) {
			assertThat(true).isEqualTo(true);
		}

	}
}
