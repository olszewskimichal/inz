package com.inz.praca.service;

import static java.util.Objects.isNull;
import static org.springframework.data.domain.Sort.Direction.fromString;
import static org.springframework.util.StringUtils.isEmpty;

import java.util.List;
import java.util.stream.Collectors;

import com.inz.praca.domain.Product;
import com.inz.praca.exceptions.ProductNotFoundException;
import com.inz.praca.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@Slf4j
public class ProductService {
	private static final int PAGE_LIMIT = 5;
	private static final int MAX_PRODUCT_ON_PAGE = 20;
	private static final String DEFAULT_SORT_BY_ID = "id";
	private static final int FIRST_PAGE = 0;

	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public Product getProductById(Long id) {
		Assert.notNull(id, "Podano pusty id produktu");
		return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
	}

	public Product getProductByName(String name) {
		Assert.notNull(name, "Podano pusta nazwe produktu");
		return productRepository.findByName(name).orElseThrow(() -> new ProductNotFoundException(name));
	}

	public List<Product> getProducts(final Integer page, final Integer size, final String sort) {
		Assert.isTrue(setReturnSize(size) >= 1, "Podano rozmiar mniejszy od 1");
		Assert.isTrue(setReturnPage(page) >= 0, "Podano nieprawidlowy numer strony");
		PageRequest pageRequest = new PageRequest(setReturnPage(page), setProductOnPageLimit(setReturnSize(size)), setSortDirection(sort), DEFAULT_SORT_BY_ID);
		log.debug("Próba pobrania produktów ze strony {} o liczbie elementow {} z sortowaniem {}", setReturnPage(page), setProductOnPageLimit(setReturnSize(size)), setSortDirection(sort));
		return productRepository.findAll(pageRequest).getContent().stream().collect(Collectors.toList());
	}

	private int setReturnPage(final Integer pageNumber) {
		return isNull(pageNumber) ? FIRST_PAGE : pageNumber - 1;
	}

	private int setReturnSize(final Integer size) {
		return isNull(size) ? PAGE_LIMIT : size;
	}

	private int setProductOnPageLimit(final Integer size) {
		return size >= MAX_PRODUCT_ON_PAGE ? MAX_PRODUCT_ON_PAGE : size;
	}

	private Sort.Direction setSortDirection(final String sort) {
		return isEmpty(sort) ? null : fromString(sort);
	}

}
