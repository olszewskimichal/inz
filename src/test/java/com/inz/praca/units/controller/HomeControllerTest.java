package com.inz.praca.units.controller;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import com.inz.praca.controller.HomeController;
import org.junit.Before;
import org.junit.Test;

public class HomeControllerTest {

	private HomeController controller;

	@Before
	public void setUp() {
		initMocks(this);
		controller = new HomeController();
	}

	@Test
	public void shouldShowMainPage() {
		assertThat(controller.mainPage()).isEqualTo("index");
	}
}
