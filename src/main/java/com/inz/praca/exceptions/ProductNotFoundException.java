package com.inz.praca.exceptions;

public class ProductNotFoundException extends RuntimeException {
	public ProductNotFoundException(String productName) {
		super("Nie znaleziono produktu o nazwie " + productName);
	}

	public ProductNotFoundException(Long id) {
		super("Nie znaleziono produktu o id " + id);
	}
}
