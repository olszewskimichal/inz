package com.inz.praca;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Category(IntegrationTest.class)
public class SklepApplicationTests {

  @Test
  public void contextLoads() {
    SklepApplication.main(new String[]{"aa"});
  }

}
