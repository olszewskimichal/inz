package com.inz.praca.category;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String name) {
        super("Nie znaleziono kategorii o nazwie " + name);
    }
}