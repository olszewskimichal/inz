package com.inz.praca.config;

import com.inz.praca.category.CategoryBuilder;
import com.inz.praca.category.CategoryRepository;
import com.inz.praca.products.ProductBuilder;
import com.inz.praca.products.ProductRepository;
import com.inz.praca.registration.User;
import com.inz.praca.registration.UserBuilder;
import com.inz.praca.registration.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Configuration
@Slf4j
@Profile("!prod")
public class DevDBConfig {

    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    public DevDBConfig(CategoryRepository categoryRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @PostConstruct
    public void populateDatabase() {
        DevDBConfig.log.info("ładowanie bazy testowej");
        this.categoryRepository.save(
                new CategoryBuilder().withName("Komputery").withDescription("Jakies super kompy").createCategory());
        this.categoryRepository.save(
                new CategoryBuilder().withName("inne").withDescription("Nie zdefiniowane").createCategory());
        User admin = new UserBuilder().withEmail("admin@email.pl").withPasswordHash("zaq1@WSX").activate().build();
        admin.giveAdminAuthorization();
        this.userRepository.save(admin);
        this.userRepository.save(new UserBuilder().withEmail("user@email.pl").withPasswordHash("zaq1@WSX").build());
        this.productRepository.save(new ProductBuilder().withPrice(BigDecimal.TEN)
                .withName("produkt")
                .withUrl("http://www.best1buy.pl/allegro/bk617e/super_produkt.gif")
                .createProduct());
    }


}
