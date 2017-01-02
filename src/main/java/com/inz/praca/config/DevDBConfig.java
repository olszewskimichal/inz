package com.inz.praca.config;

import javax.annotation.PostConstruct;

import com.inz.praca.builders.CategoryBuilder;
import com.inz.praca.repository.CategoryRepository;
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

	@PostConstruct
	public void populateDatabase() {
		log.info("ładowanie bazy testowej");
		categoryRepository.save(new CategoryBuilder().withName("Komputery").withDescription("Jakies super kompy").createCategory());
		categoryRepository.save(new CategoryBuilder().withName("inne").withDescription("Nie zdefiniowane").createCategory());
	}
}
