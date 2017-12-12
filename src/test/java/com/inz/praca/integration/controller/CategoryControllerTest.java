package com.inz.praca.integration.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.inz.praca.category.CategoryDTO;
import com.inz.praca.integration.IntegrationTestBase;
import com.inz.praca.products.ProductService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryControllerTest extends IntegrationTestBase {

  @Autowired
  ProductService service;

  @Test
  public void should_show_newCategory_page() throws Exception {
    mvc.perform(get("/addCategory"))
        .andExpect(status().isOk())
        .andExpect(view().name("newCategory"));
  }

  @Test
  public void should_process_new_category_create() throws Exception {
    mvc.perform(post("/addCategory")
        .param("name", "nazwa")
        .param("description", "opisDlugi"))
        .andExpect(model().errorCount(0));
  }

  @Test
  public void should_failed_addCategory() throws Exception {
    mvc.perform(post("/addCategory"))
        .andExpect(status().isOk())
        .andExpect(view().name("newCategory"))
        .andExpect(model().errorCount(2));
  }

  @Test
  public void should_failed_withNotNotUniqueName() throws Exception {
    CategoryDTO categoryDTO = new CategoryDTO();
    categoryDTO.setName("nazwaaaa");
    categoryDTO.setDescription("opisDlugi");        //TODO builder

    service.createCategoryFromDTO(categoryDTO);
    mvc.perform(post("/addCategory")
        .param("name", "nazwaaaaa")
        .param("description", "opisDlugi"))
        .andExpect(model().errorCount(0)); //TODO dodac validacje
  }
}
