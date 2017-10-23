package com.inz.praca.units.controller;

import com.inz.praca.UnitTest;
import com.inz.praca.category.Category;
import com.inz.praca.category.CategoryRepository;
import com.inz.praca.products.ProductBuilder;
import com.inz.praca.products.ProductController;
import com.inz.praca.products.ProductDTO;
import com.inz.praca.products.ProductService;
import com.inz.praca.utils.Pager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@org.junit.experimental.categories.Category(UnitTest.class)
public class ProductControllerTest {

    @Mock
    private Model model;

    @Mock
    private ProductService productService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BindingResult bindingResult;

    private ProductController controller;

    @Before
    public void setUp() {
        initMocks(this);
        this.controller = new ProductController(this.productService);
    }

    @Test
    public void shouldReturnRegisterPage() {
        assertThat(this.controller.addNewProductPage(this.model)).isEqualTo("newProduct");
    }

    @Test
    public void shouldCreateProductAndRedirectToProducts() {
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("name");
        productDTO.setDescription("desc");
        productDTO.setPrice(BigDecimal.TEN);
        productDTO.setImageUrl("url");

        assertThat(this.controller.confirmNewProduct(productDTO, this.bindingResult, this.model, redirectAttributes)).isEqualTo(
                "redirect:/products");
        verify(redirectAttributes).addFlashAttribute("createProductDone", true);
    }

    @Test
    public void shouldShowAgainFormWhenErrorOnCreate() {
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        given(this.bindingResult.hasErrors()).willReturn(true);

        ProductDTO productDTO = new ProductDTO();
        assertThat(this.controller.confirmNewProduct(productDTO, this.bindingResult, this.model, redirectAttributes)).isEqualTo(
                "newProduct");

        verify(this.model).addAttribute("productCreateForm", productDTO);
        verify(this.model).addAttribute("categoryList", this.productService.findAllCategory());
        verifyNoMoreInteractions(this.model);
    }

    @Test
    public void shouldShowProductDetails() {
        given(this.productService.getProductById(1L)).willReturn(
                new ProductBuilder().withName("nazwa").withPrice(BigDecimal.TEN).createProduct());
        assertThat(this.controller.showProductDetail(1L, this.model)).isEqualTo("product");
    }

    @Test
    public void shouldShowAllProducts() {
        given(this.productService.getProducts(1, 6, null, Optional.empty())).willReturn(new PageImpl<>(new ArrayList<>()));
        assertThat(this.controller.showProducts(this.model, null, null, null)).isEqualTo("products");
    }

    @Test
    public void shouldShowAllProducts2() {
        given(this.productService.getProducts(2, 6, null, Optional.empty())).willReturn(new PageImpl<>(new ArrayList<>()));
        assertThat(this.controller.showProducts(this.model, 2, 6, null)).isEqualTo("products");
    }

    @Test
    public void shouldShowAllProductsByCategory() {
        given(this.categoryRepository.findByName("kategoria")).willReturn(Optional.of(new Category("a", "a")));
        given(this.productService.getProducts(2, 6, null, Optional.ofNullable("a"))).willReturn(new PageImpl<>(new ArrayList<>()));
        assertThat(this.controller.showProducts(this.model, 2, 6, "a")).isEqualTo("products");
        verify(this.model).addAttribute("pager", new Pager(1, 1, 5));
    }

    @Test
    public void shouldShowAllProductsWithEmptyCategory() {
        given(this.productService.getProducts(1, 6, null, Optional.empty())).willReturn(new PageImpl<>(new ArrayList<>()));
        assertThat(this.controller.showProducts(this.model, null, null, "")).isEqualTo("products");
    }

    @Test
    public void shouldShowAllProducts3() {
        given(this.productService.getProducts(2, 2, null, Optional.empty())).willReturn(new PageImpl<>(new ArrayList<>()));
        assertThat(this.controller.showProducts(this.model, 2, 2, null)).isEqualTo("products");
    }

    @Test
    public void shouldReturnEditProductPage() {
        given(this.productService.getProductById(Matchers.anyLong())).willReturn(
                new ProductBuilder().withName("nazwa").withPrice(BigDecimal.TEN).createProduct());
        assertThat(this.controller.editProduct(Matchers.anyLong(), this.model)).isEqualTo("editProduct");
    }

    @Test
    public void shouldEditProductAndRedirectToProducts() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("name");
        productDTO.setDescription("desc");
        productDTO.setPrice(BigDecimal.TEN);
        productDTO.setImageUrl("url");

        assertThat(this.controller.confirmEditProduct(1L, productDTO, this.bindingResult, this.model)).isEqualTo(
                "redirect:/products/product/1");
    }

    @Test
    public void shouldShowAgainFormWhenErrorOnEdit() {
        given(this.bindingResult.hasErrors()).willReturn(true);
        ProductDTO productDTO = new ProductDTO();
        assertThat(this.controller.confirmEditProduct(1L, productDTO, this.bindingResult, this.model)).isEqualTo("editProduct");

        verify(this.model).addAttribute("productCreateForm", productDTO);
        verify(this.model).addAttribute("categoryList", this.productService.findAllCategory());
        verify(this.model).addAttribute("productId", 1L);
        verifyNoMoreInteractions(this.model);
    }

    @Test
    public void shouldDeleteProductAndRedirectToProducts() {
        assertThat(this.controller.deleteProduct(1L)).isEqualTo("redirect:/products");
    }
}
