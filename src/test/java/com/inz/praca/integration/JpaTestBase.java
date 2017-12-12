package com.inz.praca.integration;

import com.inz.praca.IntegrationTest;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@Category(IntegrationTest.class)
public abstract class JpaTestBase {

  @Autowired
  protected TestEntityManager entityManager;
}
