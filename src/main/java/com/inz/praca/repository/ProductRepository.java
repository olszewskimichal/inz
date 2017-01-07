package com.inz.praca.repository;

import java.util.List;
import java.util.Optional;

import com.inz.praca.domain.Category;
import com.inz.praca.domain.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	@Override
	Page<Product> findAll(Pageable page);

	Page<Product> findByCategory(Pageable page, Category category);

	Optional<Product> findByName(String name);

	Optional<Product> findById(Long id);

	@Query(value = "select c.name,count (p.name) from Category c,Product p where p.category.id=c.id group by c.id")
	List<Object[]> findCategoryNameAndCountProducts();
}
