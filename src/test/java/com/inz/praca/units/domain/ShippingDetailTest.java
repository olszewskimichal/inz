package com.inz.praca.units.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.inz.praca.orders.ShippingDetail;
import org.junit.Assert;
import org.junit.Test;

public class ShippingDetailTest {

	@Test
	public void shouldFailedWhenCityIsEmpty() {
		try {
			new ShippingDetail("ulica", "", "num", "code");
		}
		catch (IllegalArgumentException e) {
			assertThat(e.getMessage()).isEqualTo("Miasto nie moze byc puste");
		}
	}

	@Test
	public void shouldFailedWhenStreetIsEmpty() {
		try {
			new ShippingDetail("", "city", "num", "code");
		}
		catch (IllegalArgumentException e) {
			assertThat(e.getMessage()).isEqualTo("Ulica nie moze byc pusta");
		}
	}

	@Test
	public void shouldFailedWhenPostCodeIsEmpty() {
		try {
			new ShippingDetail("ulica", "miasto", "", "");
		}
		catch (IllegalArgumentException e) {
			assertThat(e.getMessage()).isEqualTo("Kod pocztowy nie moze byc pusty");
		}
	}

	@Test
	public void shouldCreateWhenObjectIsCorrect() {
		try {
			ShippingDetail shippingDetail = new ShippingDetail("ulica", "miasto", "46", "85-791");
			assertThat(shippingDetail.getCity()).isEqualTo("miasto");
			assertThat(shippingDetail.getStreet()).isEqualTo("ulica");
			assertThat(shippingDetail.getHouseNum()).isEqualTo("46");
			assertThat(shippingDetail.getPostCode()).isEqualTo("85-791");
		}
		catch (Exception e) {
			Assert.fail();
		}
	}

}
