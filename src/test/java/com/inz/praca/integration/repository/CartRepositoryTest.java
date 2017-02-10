package com.inz.praca.integration.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.inz.praca.builders.ProductBuilder;
import com.inz.praca.domain.Cart;
import com.inz.praca.domain.CartItem;
import com.inz.praca.domain.Product;
import com.inz.praca.integration.JpaTestBase;
import com.inz.praca.repository.CartRepository;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;

public class CartRepositoryTest extends JpaTestBase {

	@Autowired
	private CartRepository repository;

	@Test
	public void shouldFindCartById() {
		repository.deleteAll();
		Set<CartItem> cartItems = new HashSet<>();
		Product product = entityManager.persist(new ProductBuilder().withName("nameTest222").withPrice(BigDecimal.TEN).createProduct());
		cartItems.add(new CartItem(product, 1L));
		Cart cart = repository.save(new Cart(cartItems));
		Optional<Cart> cartOptional = repository.findById(cart.getId());
		assertThat(cartOptional).isNotNull();
		assertThat(cartOptional.isPresent()).isTrue();
		assertThat(cartOptional.get().getId()).isNotNull();
		assertThat(cartOptional.get().getDateTime()).isBeforeOrEqualTo(LocalDateTime.now());
		assertThat(cartOptional.get().getCartItems().size()).isEqualTo(1);
	}


	@Test
	public void shouldNotFindProductById() {
		repository.deleteAll();
		Optional<Cart> cartOptional = repository.findById(99L);
		assertThat(cartOptional).isNotNull();
		assertThat(cartOptional.isPresent()).isFalse();
	}
}
