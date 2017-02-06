package com.inz.praca.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.inz.praca.domain.Category;
import com.inz.praca.domain.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	@Override
	Page<Product> findAll(Pageable page);

	Page<Product> findByCategory(Pageable page, Category category);

	Optional<Product> findByName(String name);

	Optional<Product> findById(Long id);

	@Query(value = "select c.name,count (p.name) from Category c,Product p where p.category.id=c.id group by c.id")
	List<Object[]> findCategoryNameAndCountProducts();

	@Transactional
	@Modifying
	@Query("update Product p set p.name = ?1, p.description = ?2, p.price = ?3,p.imageUrl=?4 where p.id = ?5")
	void updateProduct(String name, String description, BigDecimal price, String imageURL, Long id);

	@Transactional
	Long deleteProductById(Long id);
}
