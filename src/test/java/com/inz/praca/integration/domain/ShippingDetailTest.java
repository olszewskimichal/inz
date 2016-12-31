package com.inz.praca.integration.domain;

import static org.assertj.core.api.Java6Assertions.assertThat;

import com.inz.praca.domain.ShippingDetail;
import com.inz.praca.integration.JpaTestBase;
import org.junit.Test;

public class ShippingDetailTest extends JpaTestBase {

	@Test
	public void shouldCreateShippingDetail() {
		ShippingDetail shippingDetail = this.entityManager.persistFlushFind(new ShippingDetail("ulica", "miasto", "46", "85-791"));
		assertThat(shippingDetail.getId()).isNotNull();
		assertThat(shippingDetail.getCity()).isEqualTo("miasto");
		assertThat(shippingDetail.getPostCode()).isEqualTo("85-791");
		assertThat(shippingDetail.getHouseNum()).isEqualTo("46");
		assertThat(shippingDetail.getStreet()).isEqualTo("ulica");
		assertThat(shippingDetail.toString()).contains("street");
	}
}
