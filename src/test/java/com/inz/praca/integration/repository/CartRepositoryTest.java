package com.inz.praca.integration.repository;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

import com.inz.praca.domain.Cart;
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
		Cart cart = repository.save(new Cart(new HashSet<>()));
		Optional<Cart> cartOptional = repository.findById(cart.getId());
		assertThat(cartOptional).isNotNull();
		assertThat(cartOptional.isPresent()).isTrue();
		assertThat(cartOptional.get().getId()).isNotNull();
		assertThat(cartOptional.get().getDateTime()).isLessThanOrEqualTo(LocalDateTime.now());
		assertThat(cartOptional.get().getCartItems().size()).isEqualTo(0);
	}


	@Test
	public void shouldNotFindProductById() {
		repository.deleteAll();
		Optional<Cart> cartOptional = repository.findById(99L);
		assertThat(cartOptional).isNotNull();
		assertThat(cartOptional.isPresent()).isFalse();
	}
}
