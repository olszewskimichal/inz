package com.inz.praca.products;

import com.inz.praca.utils.Pager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

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
        model.addAttribute(ProductController.PRODUCT_FORM, new ProductDTO());
        model.addAttribute(ProductController.CATEGORY_LIST, this.productService.findAllCategory());
        return ProductController.NEW_PRODUCT;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addProduct")
    public String confirmNewProduct(@Valid @ModelAttribute(ProductController.PRODUCT_FORM) ProductDTO productDTO, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        ProductController.log.info("Proba dodania nowego produktu {}", productDTO);

        if (result.hasErrors()) {
            ProductController.log.info("wystapil blad {} podczas walidacji produktu {}", result.getAllErrors(), productDTO);
            model.addAttribute(ProductController.PRODUCT_FORM, productDTO);
            model.addAttribute(ProductController.CATEGORY_LIST, this.productService.findAllCategory());
            return ProductController.NEW_PRODUCT;
        }
        this.productService.createProductFromDTO(productDTO);
        redirectAttributes.addFlashAttribute("createProductDone", true);
        return "redirect:/products";
    }

    @GetMapping("/products/product/{productId}")
    public String showProductDetail(@PathVariable Long productId, Model model) {
        ProductController.log.debug("Pobranie produktu o productId {}", productId);
        ProductDTO productDTO = this.productService.getProductDTOById(productId);
        model.addAttribute(productDTO);
        return ProductController.PRODUCT;
    }

    @GetMapping("/products")
    public String showProducts(Model model, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "pageSize", required = false) Integer pageSize, @RequestParam(value = "category", required = false) String category) {
        int evalPageSize = pageSize == null ? ProductController.INITIAL_PAGE_SIZE : pageSize;
        int evalPage = page == null ? ProductController.INITIAL_PAGE : page - 1;
        String evalCategory = category != null && category.trim().length() < 1 ? null : category;
        ProductController.log.info("Strona {} elementow na stronie {}", evalPage, evalPageSize);
        Page<Product> products = this.productService.getProducts(evalPage, evalPageSize, null, Optional.ofNullable(evalCategory));
        if (products == null) {
            products = new PageImpl<>(new ArrayList<>());
        }
        model.addAttribute("products", products.getContent());
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("page", products.getNumber());
        model.addAttribute("categoryList", this.productService.findAllCategory());
        model.addAttribute("selectedPageSize", evalPageSize);
        model.addAttribute("pager", new Pager(products.getTotalPages(), products.getNumber(), ProductController.BUTTONS_TO_SHOW));
        model.addAttribute("category", category);
        model.addAttribute("pageSizes", ProductController.PAGE_SIZES);
        return "products";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/products/product/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {
        ProductController.log.info("Formularz edycji produktu o id {}", id);
        ProductDTO productDTO = this.productService.getProductDTOById(id);
        model.addAttribute(ProductController.PRODUCT_FORM, productDTO);
        model.addAttribute(ProductController.CATEGORY_LIST, this.productService.findAllCategory());
        model.addAttribute(ProductController.PRODUCT_ID, id);
        return ProductController.EDIT_PRODUCT;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/products/product/delete/{productId}")
    public String deleteProduct(@PathVariable Long productId) {
        this.productService.deleteProductById(productId);   //TODO dodać feedback message
        return "redirect:/products";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/products/product/edit/{productId}")
    public String confirmEditProduct(@PathVariable Long productId, @Valid @ModelAttribute(ProductController.PRODUCT_FORM) ProductDTO productDTO, BindingResult result, Model model) {
        ProductController.log.info("Proba edycji produktu {}", productDTO);
        if (result.hasErrors()) {
            ProductController.log.info("wystapil blad {} podczas walidacji produktu {}", result.getAllErrors(), productDTO);
            model.addAttribute(ProductController.PRODUCT_FORM, productDTO);
            model.addAttribute(ProductController.CATEGORY_LIST, this.productService.findAllCategory());
            model.addAttribute(ProductController.PRODUCT_ID, productId);
            return ProductController.EDIT_PRODUCT;
        }
        this.productService.updateProduct(productId, productDTO);   //TODO dodać feedback message
        return "redirect:/products/product/" + productId;
    }
}
