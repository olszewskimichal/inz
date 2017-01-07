package com.inz.praca.dto;

import java.io.Serializable;

import com.inz.praca.domain.ShippingDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Setter
@Getter
public class OrderDTO implements Serializable {
	private CartSession cartSession;
	private ShippingDetail shippingDetail;

	public OrderDTO(CartSession cartSession, ShippingDetail shippingDetail) {
		this.cartSession = cartSession;
		this.shippingDetail = shippingDetail;
	}
}
