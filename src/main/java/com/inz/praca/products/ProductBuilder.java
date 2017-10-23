package com.inz.praca.products;

import java.math.BigDecimal;

public class ProductBuilder {
    private String name;
    private String description = "opis";
    private String imageUrl = "url";
    private BigDecimal price;
    private String category;

    public ProductBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public ProductBuilder withUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public ProductBuilder withPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public ProductBuilder withCategory(String category) {
        this.category = category;
        return this;
    }

    public Product createProduct() {
        return new Product(this.name, this.description, this.imageUrl, this.price);
    }

    public ProductDTO createProductDTO() {
        return new ProductDTO(new Product(this.name, this.description, this.imageUrl, this.price), this.category);
    }

    public Product createProduct(ProductDTO productDTO) {
        return new Product(productDTO.getName(), productDTO.getDescription(), productDTO.getImageUrl(),
                productDTO.getPrice());
    }
}