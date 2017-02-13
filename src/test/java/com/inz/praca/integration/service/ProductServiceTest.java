package com.inz.praca.integration.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.inz.praca.builders.ProductBuilder;
import com.inz.praca.domain.Cart;
import com.inz.praca.domain.CartItem;
import com.inz.praca.domain.Category;
import com.inz.praca.domain.Order;
import com.inz.praca.domain.Product;
import com.inz.praca.domain.ShippingDetail;
import com.inz.praca.dto.ProductDTO;
import com.inz.praca.integration.IntegrationTestBase;
import com.inz.praca.repository.CategoryRepository;
import com.inz.praca.repository.OrderRepository;
import com.inz.praca.repository.ProductRepository;
import com.inz.praca.service.ProductService;
import org.junit.Before;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

public class ProductServiceTest extends IntegrationTestBase {

	@Autowired
	ProductService productService;

	@Autowired
	ProductRepository repository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	OrderRepository orderRepository;

	@Before
	public void setUp() {
		orderRepository.deleteAll();
		repository.deleteAll();
	}


	@Test
	public void shouldReturn20ProductAscSortByIdWhenSizeArgumentIsEqualTo30() {
		for (int i = 0; i < 30; i++) {
			repository.save(new ProductBuilder().withName("nazwa" + i).withPrice(BigDecimal.ZERO).createProduct());
		}
		List<Product> products = this.productService.getProducts(1, 30, "desc", null).getContent();
		assertThat(products.size()).isEqualTo(20); //taki limit ustalony w ProductService
		Long firstId = products.get(0).getId();
		Long lastId = products.get(products.size() - 1).getId();
		assertThat(firstId > lastId).isTrue();
		assertThat(firstId - lastId).isEqualTo(19L);
	}

	@Test
	public void shouldCreateProductWhenDtoIsCorrect() {
		categoryRepository.save(new Category("nowa", "testowa"));
		assertThat(repository.findAll().size()).isEqualTo(0);
		ProductDTO productDTO = new ProductDTO();
		productDTO.setName("nazwa");
		productDTO.setDescription("desc");
		productDTO.setPrice(BigDecimal.TEN);
		productDTO.setImageUrl("url");
		productDTO.setCategory("nowa");

		Product product = productService.createProduct(productDTO);

		assertThat(product.getName()).isEqualTo("nazwa");
		assertThat(product.getPrice()).isEqualTo(BigDecimal.TEN);
		assertThat(product.getDescription()).isEqualTo("desc");
		assertThat(product.getImageUrl()).isEqualTo("url");
		assertThat(product.getCategory()).isNotNull();
		assertThat(repository.findAll().size()).isEqualTo(1);
	}

	@Test
	public void shouldUpdateProduct() {
		Product product = repository.save(new ProductBuilder().withName("nazwaUpdate").withPrice(BigDecimal.ZERO).createProduct());
		ProductDTO productDTO = new ProductDTO(product);
		productDTO.setPrice(BigDecimal.TEN);

		productService.updateProduct(product.getId(), productDTO);
		Product updateProduct = productService.getProductById(product.getId());
		assertThat(updateProduct.getPrice().stripTrailingZeros()).isEqualTo(BigDecimal.TEN.stripTrailingZeros());
	}

	@Test
	public void shouldDeleteProduct() {
		Product product = repository.save(new ProductBuilder().withName("nazwaUpdate").withPrice(BigDecimal.ZERO).createProduct());
		Integer size = repository.findAll().size();
		productService.deleteProductById(product.getId());
		assertThat(repository.findAll().size()).isEqualTo(size - 1);
	}

	@Test
	public void shouldSetActiveFalseWhenTryDeleteProductWhichIsOrdered() {
		Product product = repository.save(new ProductBuilder().withName("nazwaUpdate1").withPrice(BigDecimal.ZERO).createProduct());
		Product product1 = repository.save(new ProductBuilder().withName("nazwaUpdate2").withPrice(BigDecimal.ZERO).createProduct());
		Set<CartItem> cartItems = new HashSet<>();
		cartItems.add(new CartItem(product, 1L));
		orderRepository.save(new Order(new Cart(cartItems), new ShippingDetail("a", "b", "c", "d")));
		productService.deleteProductById(product.getId());
		Page<Product> products = productService.getProducts(1, null, null, null);
		assertThat(products.getTotalElements()).isEqualTo(1L);
	}
}
