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
        return new ProductDTO(productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id)));
    }

    public Product getProductById(Long id) {
        Assert.notNull(id, "Podano pusty id produktu");
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product getProductByName(String name) {
        Assert.notNull(name, "Podano pusta nazwe produktu");
        return productRepository.findByName(name).orElseThrow(() -> new ProductNotFoundException(name));
    }

    public Page<Product> getProducts(final Integer page, final Integer size, final String sort, final Optional<String> name) {
        Assert.isTrue(setReturnSize(size) >= 1, "Podano rozmiar mniejszy od 1");
        Assert.isTrue(setReturnPage(page) >= 0, "Podano nieprawidlowy numer strony");
        PageRequest pageRequest = new PageRequest(setReturnPage(page), setProductOnPageLimit(setReturnSize(size)),
                setSortDirection(sort), DEFAULT_SORT_BY_ID);
        return name.flatMap(v -> {
            Category category = categoryRepository.findByName(v)
                    .orElseThrow(() -> new CategoryNotFoundException(v));
            return Optional.of(productRepository.findByCategory(pageRequest, category));
        }).orElseGet(() -> {
            log.debug("Próba pobrania produktów ze strony {} o liczbie elementow {} z sortowaniem {}",
                    setReturnPage(page), setProductOnPageLimit(setReturnSize(size)), setSortDirection(sort));
            return productRepository.findAllByActive(pageRequest, true);
        });


    }

    @Transactional
    public Product createProductFromDTO(ProductDTO productDTO) {
        Product product = new ProductBuilder().createProduct(productDTO);
        productRepository.save(product);
        Category category = categoryRepository.findByName(productDTO.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException(productDTO.getCategory()));
        product.changeCategory(category);
        log.info("Stworzono nowy produkt {}", product);
        return product;
    }

    public Category createCategoryFromDTO(CategoryDTO categoryDTO) {
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
        productRepository.updateProduct(productDTO.getName(), productDTO.getDescription(), productDTO.getPrice(),
                productDTO.getImageUrl(), id);
    }

    public void deleteProductById(Long id) {
        try {
            productRepository.deleteProductById(id);
        } catch (DataIntegrityViolationException e) {
            log.error("Podany produkt zostal juz zamowiony wiec nie mozna go usunac ale mozna go zdeaktywowac");
            Product productById = getProductById(id);
            productById.deactivate();
            productRepository.save(productById);
        }
    }
}
