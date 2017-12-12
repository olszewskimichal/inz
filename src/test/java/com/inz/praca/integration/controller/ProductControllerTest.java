package com.inz.praca.integration.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.inz.praca.integration.IntegrationTestBase;
import com.inz.praca.products.Product;
import com.inz.praca.products.ProductBuilder;
import com.inz.praca.products.ProductService;
import java.math.BigDecimal;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class ProductControllerTest extends IntegrationTestBase {

  @Autowired
  ProductService productService;

  @Test
  public void should_show_newProduct_page() throws Exception {
    mvc.perform(get("/addProduct"))
        .andExpect(status().isOk())
        .andExpect(view().name("newProduct"));
  }

  @Test
  public void should_process_new_product_create() throws Exception {
    mvc.perform(post("/addProduct")
        .param("name", "nazwa")
        .param("imageUrl", "http://localhost")
        .param("description", "opis")
        .param("price", "1.0")
        .param("category", "inne"))
        .andExpect(model().errorCount(0));
  }

  @Test
  public void should_failed_addProduct() throws Exception {
    mvc.perform(post("/addProduct"))
        .andExpect(status().isOk())
        .andExpect(view().name("newProduct"))
        .andExpect(model().errorCount(2));
  }

  @Test
  public void should_failed_withNotCorrectPrice() throws Exception {
    mvc.perform(post("/addProduct")
        .param("name", "nazwa")
        .param("imageUrl", "http://localhost")
        .param("description", "opis")
        .param("price", "a1.0")
        .param("category", "category"))
        .andExpect(model().errorCount(1));
  }

  @Test
  public void should_failed_withNotCorrectCategory() throws Exception {
    mvc.perform(post("/addProduct")
        .param("name", "nazwa")
        .param("imageUrl", "http://localhost")
        .param("description", "opis")
        .param("price", "1.0"))
        .andExpect(model().errorCount(1));
  }

  @Test
  public void shouldShowProductDetailsPage() throws Exception {
    Product product = productService.createProductFromDTO(new ProductBuilder().withName("nameTest123")
        .withPrice(BigDecimal.TEN)
        .withCategory("inne")
        .createProductDTO());
    mvc.perform(get("/products/product/" + product.getId())
        .param("name", "nameTest123")
        .param("imageUrl", "url")
        .param("description", "desc")
        .param("price", "10"))
        .andExpect(status().isOk())
        .andExpect(view().name("product"));
  }

}
