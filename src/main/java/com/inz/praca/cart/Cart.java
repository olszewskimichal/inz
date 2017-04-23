package com.inz.praca.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@ToString
@Setter
@Slf4j
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(targetEntity = CartItem.class, cascade = CascadeType.ALL)
    @JoinTable(name = "CART_PRODUCTS",
            joinColumns = @JoinColumn(name = "CART_ID"),
            inverseJoinColumns = @JoinColumn(name = "CART_ITEM_ID"))
    private Set<CartItem> cartItems = new HashSet<>();
    private LocalDateTime dateTime;

    public Cart(Set<CartItem> cartItems) {
        Assert.notNull(cartItems, "Lista elementów koszyka nie może być nullem");
        Assert.notEmpty(cartItems, "Koszyk musi zawierac jakies produkty");
        this.cartItems = cartItems;
    }

    @PrePersist
    public void initialize() {
        this.dateTime = LocalDateTime.now();
    }
}
