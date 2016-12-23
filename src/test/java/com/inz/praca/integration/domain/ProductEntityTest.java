package com.inz.praca.integration.domain;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.math.BigDecimal;

import com.inz.praca.domain.Product;
import com.inz.praca.domain.ProductBuilder;
import com.inz.praca.integration.JpaTestBase;
import org.junit.Test;

public class ProductEntityTest extends JpaTestBase {

	@Test
	public void shouldPersistProductWhenObjectIsCorrect() {
		Product product = entityManager.persistFlushFind(new ProductBuilder().withName("nazwa").withPrice(BigDecimal.TEN).createProduct());
		assertThat(product.getName()).isEqualTo("nazwa");
		assertThat(product.getDescription()).isEqualTo("opis");
		assertThat(product.getPrice().stripTrailingZeros()).isEqualTo(BigDecimal.valueOf(10).stripTrailingZeros());
		assertThat(product.getImageUrl()).isEqualTo("url");
		assertThat(product.getId()).isNotNull();
		assertThat(product.toString()).isNotNull().isNotEmpty().contains("nazwa");
	}
}
