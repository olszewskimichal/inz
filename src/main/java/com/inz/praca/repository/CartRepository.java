package com.inz.praca.repository;

import java.util.Optional;

import com.inz.praca.domain.Cart;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
	Optional<Cart> findById(Long id);
}
