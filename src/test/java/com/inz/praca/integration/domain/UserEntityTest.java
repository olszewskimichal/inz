package com.inz.praca.integration.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.inz.praca.products.ProductBuilder;
import com.inz.praca.registration.UserBuilder;
import com.inz.praca.cart.Cart;
import com.inz.praca.cart.CartItem;
import com.inz.praca.orders.Order;
import com.inz.praca.products.Product;
import com.inz.praca.login.Role;
import com.inz.praca.orders.ShippingDetail;
import com.inz.praca.registration.User;
import com.inz.praca.integration.JpaTestBase;
import org.junit.Test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class UserEntityTest extends JpaTestBase {
	@Test
	public void shouldPersistUserWhenObjectIsCorrect() throws Exception {
		User user = this.entityManager.persistFlushFind(new UserBuilder().withEmail("prawidlowyEmail@o2.pl").withPasswordHash("zaq1@WSX").build());
		assertThat(user.getEmail()).isEqualTo("prawidlowyEmail@o2.pl");
		assertThat(user.getName()).isEqualTo("imie");
		assertThat(user.getLastName()).isEqualTo("nazwisko");
		assertThat(new BCryptPasswordEncoder().matches("zaq1@WSX", user.getPasswordHash())).isTrue();
		assertThat(user.getId()).isNotNull();
		assertThat(user.getRole()).isEqualTo(Role.USER);
		assertThat(user.toString()).isNotNull().isNotEmpty().contains("imie");
		assertThat(user.getActive()).isFalse();
	}

	@Test
	public void shouldAddOrderToUser() {
		User user = this.entityManager.persistFlushFind(new UserBuilder().withEmail("prawidlowyEmail@o2.pl").withPasswordHash("zaq1@WSX").build());
		Product product = new ProductBuilder().withName("nameTest1234").withPrice(BigDecimal.ONE).createProduct();
		this.entityManager.persist(product);

		ShippingDetail shippingDetail = new ShippingDetail("ulica", "miasto", "46", "code");
		Set<CartItem> cartItems = new HashSet<>();
		cartItems.add(new CartItem(product, 4L));
		Cart cart = new Cart(cartItems);
		user.addOrder(new Order(cart, shippingDetail));
		ShippingDetail shippingDetail2 = new ShippingDetail("ulica2", "miasto2", "46", "code");
		user.addOrder(new Order(cart, shippingDetail2));
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
	}

	@Test
	public void shouldCreateActivatedUser() {
		User user = this.entityManager.persistFlushFind(new UserBuilder().withEmail("prawidlowyEmail@o2.pl").withPasswordHash("zaq1@WSX").activate().build());
		assertThat(user.getActive()).isTrue();
	}

	@Test
	public void shouldCreateUserAsAdmin() {
		User user = new UserBuilder().withEmail("prawidlowyEmail@o2.pl").withPasswordHash("zaq1@WSX").activate().build();
		user.giveAdminAuthorization();
		this.entityManager.persistFlushFind(user);
		assertThat(user.getId()).isNotNull();
		assertThat(user.getRole()).isEqualTo(Role.ADMIN);
	}
}
