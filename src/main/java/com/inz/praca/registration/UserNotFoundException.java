package com.inz.praca.registration;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String email) {
        super("Nie znaleziono uzytkownika o emailu = " + email);
    }

    public UserNotFoundException(Long id) {
        super("Nie znaleziono uzytkownika o id = " + id);
    }
}
