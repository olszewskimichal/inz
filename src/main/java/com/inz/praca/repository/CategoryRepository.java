package com.inz.praca.repository;

import java.util.Optional;

import com.inz.praca.domain.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	Optional<Category> findByName(String name);
}
