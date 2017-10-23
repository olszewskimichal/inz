package com.inz.praca.products;

import com.inz.praca.category.Category;
import com.inz.praca.category.CategoryDTO;
import com.inz.praca.category.CategoryNotFoundException;
import com.inz.praca.category.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static org.springframework.data.domain.Sort.Direction.fromString;
import static org.springframework.util.StringUtils.isEmpty;

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

    public ProductDTO getProductDTOById(Long id) {
        Assert.notNull(id, "Podano pusty id produktu");
        return new ProductDTO(this.productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id)));
    }

    public Product getProductById(Long id) {
        Assert.notNull(id, "Podano pusty id produktu");
        return this.productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product getProductByName(String name) {
        Assert.notNull(name, "Podano pusta nazwe produktu");
        return this.productRepository.findByName(name).orElseThrow(() -> new ProductNotFoundException(name));
    }

    public Page<Product> getProducts(Integer page, Integer size, String sort, Optional<String> name) {
        Assert.isTrue(this.setReturnSize(size) >= 1, "Podano rozmiar mniejszy od 1");
        Assert.isTrue(this.setReturnPage(page) >= 0, "Podano nieprawidlowy numer strony");
        PageRequest pageRequest = new PageRequest(this.setReturnPage(page), this.setProductOnPageLimit(this.setReturnSize(size)),
                this.setSortDirection(sort), ProductService.DEFAULT_SORT_BY_ID);
        return name.flatMap(v -> {
            Category category = this.categoryRepository.findByName(v)
                    .orElseThrow(() -> new CategoryNotFoundException(v));
            return Optional.of(this.productRepository.findByCategory(pageRequest, category));
        }).orElseGet(() -> {
            ProductService.log.debug("Próba pobrania produktów ze strony {} o liczbie elementow {} z sortowaniem {}",
                    this.setReturnPage(page), this.setProductOnPageLimit(this.setReturnSize(size)), this.setSortDirection(sort));
            return this.productRepository.findAllByActive(pageRequest, true);
        });


    }

    @Transactional
    public Product createProductFromDTO(ProductDTO productDTO) {
        Product product = new ProductBuilder().createProduct(productDTO);
        this.productRepository.save(product);
        Category category = this.categoryRepository.findByName(productDTO.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException(productDTO.getCategory()));
        product.changeCategory(category);
        ProductService.log.info("Stworzono nowy produkt {}", product);
        return product;
    }

    public Category createCategoryFromDTO(CategoryDTO categoryDTO) {
        Category category = new Category(categoryDTO.getName(), categoryDTO.getDescription());
        this.categoryRepository.save(category);
        ProductService.log.debug("Stworzono kategorie o id {} ", category.getId());
        return category;
    }

    public List<Category> findAllCategory() {
        return this.categoryRepository.findAll();
    }

    private int setReturnPage(Integer pageNumber) {
        return isNull(pageNumber) ? ProductService.FIRST_PAGE : pageNumber;
    }

    private int setReturnSize(Integer size) {
        return isNull(size) ? ProductService.PAGE_LIMIT : size;
    }

    private int setProductOnPageLimit(Integer size) {
        return size > ProductService.MAX_PRODUCT_ON_PAGE - 1 ? ProductService.MAX_PRODUCT_ON_PAGE : size;
    }

    private Direction setSortDirection(String sort) {
        return isEmpty(sort) ? null : fromString(sort);
    }

    public void updateProduct(Long id, ProductDTO productDTO) {
        this.productRepository.updateProduct(productDTO.getName(), productDTO.getDescription(), productDTO.getPrice(),
                productDTO.getImageUrl(), id);
    }

    public void deleteProductById(Long id) {
        try {
            ProductService.log.info("Usuwanie produktu o id {}", id);
            this.productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
            this.productRepository.deleteProductById(id);
        } catch (DataIntegrityViolationException e) {
            ProductService.log.error("Podany produkt zostal juz zamowiony wiec nie mozna go usunac ale mozna go zdeaktywowac");
            Product productById = this.getProductById(id);
            productById.deactivate();
            this.productRepository.save(productById);
        }
    }
}
