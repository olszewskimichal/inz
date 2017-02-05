package com.inz.praca.integration.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.inz.praca.builders.ProductBuilder;
import com.inz.praca.domain.Cart;
import com.inz.praca.domain.CartItem;
import com.inz.praca.domain.Order;
import com.inz.praca.domain.Product;
import com.inz.praca.domain.ShippingDetail;
import com.inz.praca.domain.User;
import com.inz.praca.domain.UserBuilder;
import com.inz.praca.integration.JpaTestBase;
import org.junit.Test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class UserEntityTest extends JpaTestBase {
	@Test
	public void shouldPersistUserWhenObjectIsCorrect() throws Exception {
		User user = this.entityManager.persistFlushFind(new UserBuilder().withEmail("prawidlowyEmail@o2.pl").withPasswordHash("hash").build());
		assertThat(user.getEmail()).isEqualTo("prawidlowyEmail@o2.pl");
		assertThat(user.getName()).isEqualTo("imie");
		assertThat(user.getLastName()).isEqualTo("nazwisko");
		assertThat(new BCryptPasswordEncoder().matches("hash", user.getPasswordHash())).isTrue();
		assertThat(user.getId()).isNotNull();
		assertThat(user.toString()).isNotNull().isNotEmpty().contains("imie");
	}

	@Test
	public void shouldAddOrderToUser() {
		User user = this.entityManager.persistFlushFind(new UserBuilder().withEmail("prawidlowyEmail@o2.pl").withPasswordHash("hash").build());
		ShippingDetail shippingDetail = new ShippingDetail("ulica", "miasto", "46", "code");
		Set<CartItem> cartItems = new HashSet<>();
		Product product = new ProductBuilder().withName("name").withPrice(BigDecimal.ONE).createProduct();
		cartItems.add(new CartItem(product, 4L));
		Cart cart = new Cart(cartItems);
		user.getOrders().add(new Order(cart, shippingDetail));
		ShippingDetail shippingDetail2 = new ShippingDetail("ulica2", "miasto2", "46", "code");
		user.getOrders().add(new Order(cart, shippingDetail2));
		this.entityManager.persistAndFlush(user);
		assertThat(user.getOrders().size()).isEqualTo(2);
		for (Order order : user.getOrders()) {
			assertThat(order.getId()).isNotNull();
			assertThat(order.getShippingDetail()).isNotNull();
			assertThat(order.getShippingDetail().getId()).isNotNull();
			assertThat(order.getCart()).isNotNull();
			assertThat(order.getPrice().stripTrailingZeros()).isEqualTo(BigDecimal.valueOf(4).stripTrailingZeros());
			assertThat(order.getCart().getId()).isNotNull();
			assertThat(order.getCart().getDateTime()).isNotNull().isBeforeOrEqualTo(LocalDateTime.now());
		}

		user.getOrders().removeIf(v -> v.getShippingDetail().getStreet().equalsIgnoreCase("ulica2"));
		this.entityManager.persist(user);
		User user1 = this.entityManager.find(User.class, user.getId());
		assertThat(user1.getOrders().size()).isEqualTo(1);
	}
}
