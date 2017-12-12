package com.inz.praca.integration.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.inz.praca.integration.JpaTestBase;
import com.inz.praca.products.ProductBuilder;
import java.math.BigDecimal;
import javax.persistence.PersistenceException;
import org.junit.Assert;
import org.junit.Test;

public class ProductEntityTest extends JpaTestBase {

  @Test
  public void shouldNotPersistWithNotUniqueName() {
    try {
      entityManager.persistFlushFind(
          new ProductBuilder().withName("nazwax12345").withPrice(BigDecimal.TEN).createProduct());
      entityManager.persistFlushFind(
          new ProductBuilder().withName("nazwax12345").withPrice(BigDecimal.ONE).createProduct());
      Assert.fail();
    } catch (PersistenceException e) {
      assertThat(true).isEqualTo(true);
    }

  }

}
