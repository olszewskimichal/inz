package com.inz.praca.orders;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

import com.inz.praca.cart.Cart;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.util.Assert;

@Entity
@Getter
@Setter
@ToString
@Table(name = "ORDERS")
public class Order {
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "CART_ID")
	private final Cart cart;
	private final BigDecimal price;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "DETAIL_ID")
	private final ShippingDetail shippingDetail;
	@Id
	@GeneratedValue
	private Long id;

	public Order(Cart cart, ShippingDetail shippingDetail) {
		Assert.notNull(cart, "Zamowienie musi zawierac jakis koszyk");
		Assert.notNull(shippingDetail, "Zamowienie musi zawierac dane dostawy");
		this.price = cart.getCartItems().stream().map(v -> v.getProduct().getPrice().multiply(BigDecimal.valueOf(v.getQuantity()))).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
		this.cart = cart;
		this.shippingDetail = shippingDetail;
	}

	private Order() {
		this.cart = null;
		this.price = BigDecimal.ZERO;
		this.shippingDetail = null;
	}
}
