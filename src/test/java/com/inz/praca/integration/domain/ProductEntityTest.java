package com.inz.praca.integration.domain;

import com.inz.praca.domain.Product;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ProductEntityTest extends EntityTestBase {

    @Test
    public void shouldPersistProductWhenObjectIsCorrect(){
        Product product = entityManager.persistFlushFind(new Product("nazwa", "opis", "url", BigDecimal.TEN));
        assertThat(product.getName()).isEqualTo("nazwa");
        assertThat(product.getDescription()).isEqualTo("opis");
        assertThat(product.getPrice().stripTrailingZeros()).isEqualTo(BigDecimal.valueOf(10).stripTrailingZeros());
        assertThat(product.getImageUrl()).isEqualTo("url");
        assertThat(product.getId()).isNotNull();
        assertThat(product.toString()).isNotNull().isNotEmpty().contains("nazwa");
    }
}
