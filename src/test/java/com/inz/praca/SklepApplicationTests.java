package com.inz.praca;

import org.junit.Test;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class SklepApplicationTests {

	@Test
	public void contextLoads() {
		SklepApplication sklepApplication = new SklepApplication();
		sklepApplication.main(new String[] {"aa"});
	}

}
