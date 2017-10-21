package com.inz.praca.registration;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Szukany uzytkownik nie istnieje")
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String email) {
        super("Nie znaleziono uzytkownika o emailu = " + email);
    }

    public UserNotFoundException(Long id) {
        super("Nie znaleziono uzytkownika o id = " + id);
    }
}
