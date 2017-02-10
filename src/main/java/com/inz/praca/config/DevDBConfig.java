package com.inz.praca.config;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

import com.inz.praca.builders.CategoryBuilder;
import com.inz.praca.builders.ProductBuilder;
import com.inz.praca.domain.Role;
import com.inz.praca.domain.User;
import com.inz.praca.builders.UserBuilder;
import com.inz.praca.repository.CategoryRepository;
import com.inz.praca.repository.ProductRepository;
import com.inz.praca.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Slf4j
@Profile("!prod")
public class DevDBConfig {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;

	@PostConstruct
	public void populateDatabase() {
		log.info("ładowanie bazy testowej");
		categoryRepository.save(new CategoryBuilder().withName("Komputery").withDescription("Jakies super kompy").createCategory());
		categoryRepository.save(new CategoryBuilder().withName("inne").withDescription("Nie zdefiniowane").createCategory());
		User admin = new UserBuilder().withEmail("admin@email.pl").withPasswordHash("zaq1@WSX").build();
		admin.setRole(Role.ADMIN);
		userRepository.save(admin);
		userRepository.save(new UserBuilder().withEmail("user@email.pl").withPasswordHash("zaq1@WSX").build());

		productRepository.save(new ProductBuilder().withPrice(BigDecimal.TEN).withName("name").createProduct());
	}
}
