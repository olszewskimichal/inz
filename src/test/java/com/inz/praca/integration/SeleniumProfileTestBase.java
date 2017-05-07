package com.inz.praca.integration;

import com.inz.praca.IntegrationTest;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("development")
@ContextConfiguration
@Category(IntegrationTest.class)
public abstract class SeleniumProfileTestBase {

    @LocalServerPort
    public int port;

}
