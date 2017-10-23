package com.inz.praca.integration.repository;

import com.inz.praca.category.Category;
import com.inz.praca.category.CategoryBuilder;
import com.inz.praca.integration.JpaTestBase;
import com.inz.praca.products.Product;
import com.inz.praca.products.ProductBuilder;
import com.inz.praca.products.ProductRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductRepositoryTest extends JpaTestBase {

    private static final String NAME = "nazwa";
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void shouldFindProductByName() {
        this.productRepository.deleteAll();
        entityManager.persistAndFlush(
                new ProductBuilder().withName(ProductRepositoryTest.NAME).withPrice(BigDecimal.TEN).createProduct());
        Optional<Product> byName = productRepository.findByName(ProductRepositoryTest.NAME);
        assertThat(byName).isNotNull();
        assertThat(byName.isPresent()).isTrue();
        assertThat(byName.get().getName()).isEqualTo(ProductRepositoryTest.NAME);
    }

    @Test
    public void shouldFindAllPageable() {
        this.productRepository.deleteAll();
        entityManager.persist(new ProductBuilder().withName("nazwa1").withPrice(BigDecimal.TEN).createProduct());
        entityManager.persist(new ProductBuilder().withName("nazwa2").withPrice(BigDecimal.TEN).createProduct());
        entityManager.persist(new ProductBuilder().withName("nazwa3").withPrice(BigDecimal.TEN).createProduct());
        List<Product> content = productRepository.findAllByActive(new PageRequest(0, 2), true).getContent();
        assertThat(content.size()).isEqualTo(2);
        assertThat(content.get(0).getName()).isEqualTo("nazwa1");
        assertThat(content.get(1).getName()).isEqualTo("nazwa2");

        content = productRepository.findAllByActive(new PageRequest(1, 2), true).getContent();
        assertThat(content.size()).isEqualTo(1);
        assertThat(content.get(0).getName()).isEqualTo("nazwa3");
    }

    @Test
    public void shouldCountProductByCategory() {
        this.productRepository.deleteAll();

        Category category = new CategoryBuilder().withName("nazwa1").createCategory();
        Category category2 = new CategoryBuilder().withName("nazwa2").createCategory();
        this.entityManager.persistAndFlush(category);
        this.entityManager.persistAndFlush(category2);
        Product product = new ProductBuilder().withName("nazwa")
                .withPrice(BigDecimal.TEN)
                .withCategory("nazwa1")
                .createProduct();
        product.changeCategory(category);
        Product product1 = new ProductBuilder().withName("nazwa1")
                .withPrice(BigDecimal.TEN)
                .withCategory("nazwa1")
                .createProduct();
        product1.changeCategory(category);
        Product product2 = new ProductBuilder().withName("nazwa2")
                .withPrice(BigDecimal.TEN)
                .withCategory("nazwa2")
                .createProduct();
        product2.changeCategory(category2);
        this.entityManager.persistAndFlush(product);
        this.entityManager.persistAndFlush(product1);
        this.entityManager.persistAndFlush(product2);

        List<Object[]> categoryNameAndCountProducts = this.productRepository.findCategoryNameAndCountProducts();
        assertThat((String) categoryNameAndCountProducts.get(0)[0]).isEqualTo("nazwa1");
        assertThat((Long) categoryNameAndCountProducts.get(0)[1]).isEqualTo(2L);
        assertThat((String) categoryNameAndCountProducts.get(1)[0]).isEqualTo("nazwa2");
        assertThat((Long) categoryNameAndCountProducts.get(1)[1]).isEqualTo(1L);
    }
}
