package com.inz.praca.products;

import com.inz.praca.utils.Pager;
import java.util.ArrayList;
import java.util.Optional;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class ProductController {

  private static final String NEW_PRODUCT = "newProduct";
  private static final String EDIT_PRODUCT = "editProduct";
  private static final String PRODUCT_FORM = "productCreateForm";
  private static final String PRODUCT = "product";
  private static final String CATEGORY_LIST = "categoryList";
  private static final String PRODUCT_ID = "productId";
  private static final int BUTTONS_TO_SHOW = 5;
  private static final int INITIAL_PAGE = 0;
  private static final int INITIAL_PAGE_SIZE = 6;
  private static final int[] PAGE_SIZES = {6, 12, 18};
  private final ProductService productService;


  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping("/addProduct")
  public String addNewProductPage(Model model) {
    model.addAttribute(PRODUCT_FORM, new ProductDTO());
    model.addAttribute(CATEGORY_LIST, productService.findAllCategory());
    return NEW_PRODUCT;
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping("/addProduct")
  public String confirmNewProduct(@Valid @ModelAttribute(PRODUCT_FORM) ProductDTO productDTO, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
    log.info("Proba dodania nowego produktu {}", productDTO);

    if (result.hasErrors()) {
      log.info("wystapil blad {} podczas walidacji produktu {}", result.getAllErrors(), productDTO);
      model.addAttribute(PRODUCT_FORM, productDTO);
      model.addAttribute(CATEGORY_LIST, productService.findAllCategory());
      return NEW_PRODUCT;
    }
    productService.createProductFromDTO(productDTO);
    redirectAttributes.addFlashAttribute("createProductDone", true);
    return "redirect:/products";
  }

  @GetMapping("/products/product/{productId}")
  public String showProductDetail(@PathVariable Long productId, Model model) {
    log.debug("Pobranie produktu o productId {}", productId);
    ProductDTO productDTO = productService.getProductDTOById(productId);
    model.addAttribute(productDTO);
    return PRODUCT;
  }

  @GetMapping("/products")
  public String showProducts(Model model, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "pageSize", required = false) Integer pageSize,
      @RequestParam(value = "category", required = false) String category) {
    int evalPageSize = pageSize == null ? INITIAL_PAGE_SIZE : pageSize;
    int evalPage = page == null ? INITIAL_PAGE : page - 1;
    String evalCategory = category != null && category.trim().length() < 1 ? null : category;
    log.info("Strona {} elementow na stronie {}", evalPage, evalPageSize);
    Page<Product> products = productService.getProducts(evalPage, evalPageSize, null, Optional.ofNullable(evalCategory));
    if (products == null) {
      products = new PageImpl<>(new ArrayList<>());
    }
    model.addAttribute("products", products.getContent());
    model.addAttribute("totalPages", products.getTotalPages());
    model.addAttribute("page", products.getNumber());
    model.addAttribute("categoryList", productService.findAllCategory());
    model.addAttribute("selectedPageSize", evalPageSize);
    model.addAttribute("pager", new Pager(products.getTotalPages(), products.getNumber(), BUTTONS_TO_SHOW));
    model.addAttribute("category", category);
    model.addAttribute("pageSizes", PAGE_SIZES);
    return "products";
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping("/products/product/edit/{id}")
  public String editProduct(@PathVariable Long id, Model model) {
    log.info("Formularz edycji produktu o id {}", id);
    ProductDTO productDTO = productService.getProductDTOById(id);
    model.addAttribute(PRODUCT_FORM, productDTO);
    model.addAttribute(CATEGORY_LIST, productService.findAllCategory());
    model.addAttribute(PRODUCT_ID, id);
    return EDIT_PRODUCT;
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping("/products/product/delete/{productId}")
  public String deleteProduct(@PathVariable Long productId) {
    productService.deleteProductById(productId);   //TODO dodać feedback message
    return "redirect:/products";
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping("/products/product/edit/{productId}")
  public String confirmEditProduct(@PathVariable Long productId, @Valid @ModelAttribute(PRODUCT_FORM) ProductDTO productDTO, BindingResult result, Model model) {
    log.info("Proba edycji produktu {}", productDTO);
    if (result.hasErrors()) {
      log.info("wystapil blad {} podczas walidacji produktu {}", result.getAllErrors(), productDTO);
      model.addAttribute(PRODUCT_FORM, productDTO);
      model.addAttribute(CATEGORY_LIST, productService.findAllCategory());
      model.addAttribute(PRODUCT_ID, productId);
      return EDIT_PRODUCT;
    }
    productService.updateProduct(productId, productDTO);   //TODO dodać feedback message
    return "redirect:/products/product/" + productId;
  }
}
