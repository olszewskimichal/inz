package com.inz.praca.products;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Szukany product nie istnieje")
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String productName) {
        super("Nie znaleziono produktu o nazwie " + productName);
    }

    public ProductNotFoundException(Long id) {
        super("Nie znaleziono produktu o id " + id);
    }
}
