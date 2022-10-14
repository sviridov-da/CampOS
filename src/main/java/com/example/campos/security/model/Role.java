package com.example.campos.security.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN, CREATOR, EMPLOYER;

    @Override
    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
