package com.inz.praca.units.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;

import com.inz.praca.UnitTest;
import com.inz.praca.category.CategoryController;
import com.inz.praca.category.CategoryDTO;
import com.inz.praca.products.ProductService;
import javax.persistence.PersistenceException;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Category(UnitTest.class)
public class CategoryControllerTest {

  private CategoryController controller;

  @Mock
  private Model model;

  @Mock
  private BindingResult bindingResult;

  @Mock
  private ProductService productService;

  @Before
  public void setUp() {
    initMocks(this);
    controller = new CategoryController(productService);
  }

  @Test
  public void shouldReturnNewCategoryPage() {
    assertThat(controller.addNewCategoryPage(model)).isEqualTo("newCategory");
  }

  @Test
  public void shouldCreateCategoryAndRedirectToIndex() {
    CategoryDTO categoryDTO = new CategoryDTO();
    categoryDTO.setName("name");
    categoryDTO.setName("desc");
    assertThat(controller.confirmNewCategory(categoryDTO, bindingResult, model)).isEqualTo("redirect:/");
  }

  @Test
  public void shouldFailedCreateCategory() {
    given(productService.createCategoryFromDTO(any(CategoryDTO.class))).willThrow(new PersistenceException());
    CategoryDTO categoryDTO = new CategoryDTO();
    assertThat(controller.confirmNewCategory(categoryDTO, bindingResult, model)).isEqualTo("newCategory");
    verify(model).addAttribute("categoryCreateForm", categoryDTO);
    verifyNoMoreInteractions(model);
  }

  @Test
  public void shouldShowAgainFormWhenErrorOnCreate() {
    given(bindingResult.hasErrors()).willReturn(true);
    CategoryDTO categoryDTO = new CategoryDTO();
    assertThat(controller.confirmNewCategory(categoryDTO, bindingResult, model)).isEqualTo("newCategory");
    verify(model).addAttribute("categoryCreateForm", categoryDTO);
    verifyNoMoreInteractions(model);
  }

}
