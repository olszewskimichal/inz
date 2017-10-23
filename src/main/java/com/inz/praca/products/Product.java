package com.inz.praca.products;

import com.inz.praca.category.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@ToString
@Getter
@Slf4j
@NoArgsConstructor(force = true)
public class Product {
    @Column(unique = true)
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final String imageUrl;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CATEGORY_ID")
    public Category category;
    @Column(columnDefinition = "tinyint(1) default 1")
    private Boolean active;
    @Id
    @GeneratedValue
    private Long id;

    public Product(String name, String description, String imageUrl, BigDecimal price) {
        Assert.hasLength(name, "Nie moze być pusta nazwa produktu");
        Assert.notNull(price, "Cena nie moze byc nullem");
        Assert.isTrue(price.compareTo(BigDecimal.ZERO) >= 0, "Cena nie moze być mniejsza od 0");
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        active = true;
    }

    public void deactivate() {
        active = false;
    }

    public void changeCategory(Category category) {
        this.category = category;
    }
}
