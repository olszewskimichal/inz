package com.inz.praca.service;

import static java.util.Objects.isNull;
import static org.springframework.data.domain.Sort.Direction.fromString;
import static org.springframework.util.StringUtils.isEmpty;

import java.util.List;

import com.inz.praca.builders.ProductBuilder;
import com.inz.praca.domain.Category;
import com.inz.praca.domain.Product;
import com.inz.praca.dto.CategoryDTO;
import com.inz.praca.dto.ProductDTO;
import com.inz.praca.exceptions.CategoryNotFoundException;
import com.inz.praca.exceptions.ProductNotFoundException;
import com.inz.praca.repository.CategoryRepository;
import com.inz.praca.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Slf4j
public class ProductService {
	private static final int PAGE_LIMIT = 5;
	private static final int MAX_PRODUCT_ON_PAGE = 20;
	private static final String DEFAULT_SORT_BY_ID = "id";
	private static final int FIRST_PAGE = 0;

	private final ProductRepository productRepository;

	private final CategoryRepository categoryRepository;

	public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
	}

	public Product getProductById(Long id) {
		Assert.notNull(id, "Podano pusty id produktu");
		return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
	}

	public Product getProductByName(String name) {
		Assert.notNull(name, "Podano pusta nazwe produktu");
		return productRepository.findByName(name).orElseThrow(() -> new ProductNotFoundException(name));
	}

	public Page<Product> getProducts(final Integer page, final Integer size, final String sort, final String name) {
		Assert.isTrue(setReturnSize(size) >= 1, "Podano rozmiar mniejszy od 1");
		Assert.isTrue(setReturnPage(page) >= 0, "Podano nieprawidlowy numer strony");
		PageRequest pageRequest = new PageRequest(setReturnPage(page), setProductOnPageLimit(setReturnSize(size)), setSortDirection(sort), DEFAULT_SORT_BY_ID);
		if (name != null) {
			Category category = categoryRepository.findByName(name).orElseThrow(() -> new CategoryNotFoundException(name));
			return productRepository.findByCategory(pageRequest, category);
		}
		log.debug("Próba pobrania produktów ze strony {} o liczbie elementow {} z sortowaniem {}", setReturnPage(page), setProductOnPageLimit(setReturnSize(size)), setSortDirection(sort));
		return productRepository.findAllByActive(pageRequest, true);
	}

	@Transactional
	public Product createProduct(ProductDTO productDTO) {
		Product product = new ProductBuilder().createProduct(productDTO);
		productRepository.save(product);
		Category category = categoryRepository.findByName(productDTO.getCategory()).orElseThrow(() -> new CategoryNotFoundException(productDTO.getCategory()));
		product.setCategory(category);
		log.info("Stworzono nowy produkt {}", product);
		return product;
	}

	public Category createCategory(CategoryDTO categoryDTO) {
		Category category = new Category(categoryDTO.getName(), categoryDTO.getDescription());
		categoryRepository.save(category);
		log.debug("Stworzono kategorie o id {} ", category.getId());
		return category;
	}

	public List<Category> findAllCategory() {
		return categoryRepository.findAll();
	}

	private int setReturnPage(final Integer pageNumber) {
		return isNull(pageNumber) ? FIRST_PAGE : pageNumber - 1;
	}

	private int setReturnSize(final Integer size) {
		return isNull(size) ? PAGE_LIMIT : size;
	}

	private int setProductOnPageLimit(final Integer size) {
		return size > MAX_PRODUCT_ON_PAGE - 1 ? MAX_PRODUCT_ON_PAGE : size;
	}

	private Sort.Direction setSortDirection(final String sort) {
		return isEmpty(sort) ? null : fromString(sort);
	}

	public void updateProduct(Long id, ProductDTO productDTO) {
		productRepository.updateProduct(productDTO.getName(), productDTO.getDescription(), productDTO.getPrice(), productDTO.getImageUrl(), id);
	}

	public Long deleteProductById(Long id) {
		try {
			productRepository.deleteProductById(id);
		}
		catch (DataIntegrityViolationException e) {
			log.error("Podany produkt zostal juz zamowiony wiec nie mozna go usunac ale mozna go zdeaktywowac");
			Product productById = getProductById(id);
			productById.setActive(false);
			productRepository.save(productById);
		}
		return id;
	}
}
