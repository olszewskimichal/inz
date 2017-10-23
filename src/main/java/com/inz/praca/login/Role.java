package com.inz.praca.login;

import org.springframework.security.core.GrantedAuthority;


public enum Role implements GrantedAuthority {
    USER("user"),

    ADMIN("admin");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    @Override
    public String getAuthority() {
        return this.name();
    }
}
