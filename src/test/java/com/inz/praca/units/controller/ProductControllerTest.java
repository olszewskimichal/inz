package com.inz.praca.units.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;

import com.inz.praca.UnitTest;
import com.inz.praca.category.Category;
import com.inz.praca.category.CategoryRepository;
import com.inz.praca.products.ProductBuilder;
import com.inz.praca.products.ProductController;
import com.inz.praca.products.ProductDTO;
import com.inz.praca.products.ProductService;
import com.inz.praca.utils.Pager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    controller = new ProductController(productService);
  }

  @Test
  public void shouldReturnRegisterPage() {
    assertThat(controller.addNewProductPage(model)).isEqualTo("newProduct");
  }

  @Test
  public void shouldCreateProductAndRedirectToProducts() {
    RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
    ProductDTO productDTO = new ProductDTO();
    productDTO.setName("name");
    productDTO.setDescription("desc");
    productDTO.setPrice(BigDecimal.TEN);
    productDTO.setImageUrl("url");

    assertThat(controller.confirmNewProduct(productDTO, bindingResult, model, redirectAttributes)).isEqualTo(
        "redirect:/products");
    verify(redirectAttributes).addFlashAttribute("createProductDone", true);
  }

  @Test
  public void shouldShowAgainFormWhenErrorOnCreate() {
    RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
    given(bindingResult.hasErrors()).willReturn(true);

    ProductDTO productDTO = new ProductDTO();
    assertThat(controller.confirmNewProduct(productDTO, bindingResult, model, redirectAttributes)).isEqualTo(
        "newProduct");

    verify(model).addAttribute("productCreateForm", productDTO);
    verify(model).addAttribute("categoryList", productService.findAllCategory());
    verifyNoMoreInteractions(model);
  }

  @Test
  public void shouldShowProductDetails() {
    given(productService.getProductById(1L)).willReturn(
        new ProductBuilder().withName("nazwa").withPrice(BigDecimal.TEN).createProduct());
    assertThat(controller.showProductDetail(1L, model)).isEqualTo("product");
  }

  @Test
  public void shouldShowAllProducts() {
    given(productService.getProducts(1, 6, null, Optional.empty())).willReturn(new PageImpl<>(new ArrayList<>()));
    assertThat(controller.showProducts(model, null, null, null)).isEqualTo("products");
  }

  @Test
  public void shouldShowAllProducts2() {
    given(productService.getProducts(2, 6, null, Optional.empty())).willReturn(new PageImpl<>(new ArrayList<>()));
    assertThat(controller.showProducts(model, 2, 6, null)).isEqualTo("products");
  }

  @Test
  public void shouldShowAllProductsByCategory() {
    given(categoryRepository.findByName("kategoria")).willReturn(Optional.of(new Category("a", "a")));
    given(productService.getProducts(2, 6, null, Optional.of("a"))).willReturn(new PageImpl<>(new ArrayList<>()));
    assertThat(controller.showProducts(model, 2, 6, "a")).isEqualTo("products");
    verify(model).addAttribute("pager", new Pager(1, 1, 5));
  }

  @Test
  public void shouldShowAllProductsWithEmptyCategory() {
    given(productService.getProducts(1, 6, null, Optional.empty())).willReturn(new PageImpl<>(new ArrayList<>()));
    assertThat(controller.showProducts(model, null, null, "")).isEqualTo("products");
  }

  @Test
  public void shouldShowAllProducts3() {
    given(productService.getProducts(2, 2, null, Optional.empty())).willReturn(new PageImpl<>(new ArrayList<>()));
    assertThat(controller.showProducts(model, 2, 2, null)).isEqualTo("products");
  }

  @Test
  public void shouldReturnEditProductPage() {
    given(productService.getProductById(Matchers.anyLong())).willReturn(
        new ProductBuilder().withName("nazwa").withPrice(BigDecimal.TEN).createProduct());
    assertThat(controller.editProduct(Matchers.anyLong(), model)).isEqualTo("editProduct");
  }

  @Test
  public void shouldEditProductAndRedirectToProducts() {
    ProductDTO productDTO = new ProductDTO();
    productDTO.setName("name");
    productDTO.setDescription("desc");
    productDTO.setPrice(BigDecimal.TEN);
    productDTO.setImageUrl("url");

    assertThat(controller.confirmEditProduct(1L, productDTO, bindingResult, model)).isEqualTo(
        "redirect:/products/product/1");
  }

  @Test
  public void shouldShowAgainFormWhenErrorOnEdit() {
    given(bindingResult.hasErrors()).willReturn(true);
    ProductDTO productDTO = new ProductDTO();
    assertThat(controller.confirmEditProduct(1L, productDTO, bindingResult, model)).isEqualTo("editProduct");

    verify(model).addAttribute("productCreateForm", productDTO);
    verify(model).addAttribute("categoryList", productService.findAllCategory());
    verify(model).addAttribute("productId", 1L);
    verifyNoMoreInteractions(model);
  }

  @Test
  public void shouldDeleteProductAndRedirectToProducts() {
    assertThat(controller.deleteProduct(1L)).isEqualTo("redirect:/products");
  }
}
