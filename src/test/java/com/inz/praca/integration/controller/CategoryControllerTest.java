package com.inz.praca.integration.controller;

import com.inz.praca.category.CategoryDTO;
import com.inz.praca.integration.IntegrationTestBase;
import com.inz.praca.products.ProductService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CategoryControllerTest extends IntegrationTestBase {

    @Autowired
    ProductService service;

    @Test
    public void should_show_newCategory_page() throws Exception {
        this.mvc.perform(get("/addCategory"))
                .andExpect(status().isOk())
                .andExpect(view().name("newCategory"));
    }

    @Test
    public void should_process_new_category_create() throws Exception {
        this.mvc.perform(post("/addCategory")
                .param("name", "nazwa")
                .param("description", "opisDlugi"))
                .andExpect(model().errorCount(0));
    }

    @Test
    public void should_failed_addCategory() throws Exception {
        this.mvc.perform(post("/addCategory"))
                .andExpect(status().isOk())
                .andExpect(view().name("newCategory"))
                .andExpect(model().errorCount(2));
    }

    @Test
    public void should_failed_withNotNotUniqueName() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("nazwaaaa");
        categoryDTO.setDescription("opisDlugi");        //TODO builder

        this.service.createCategoryFromDTO(categoryDTO);
        this.mvc.perform(post("/addCategory")
                .param("name", "nazwaaaaa")
                .param("description", "opisDlugi"))
                .andExpect(model().errorCount(0)); //TODO dodac validacje
    }
}
