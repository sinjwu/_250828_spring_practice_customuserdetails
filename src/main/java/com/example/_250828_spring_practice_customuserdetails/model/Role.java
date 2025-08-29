package com.example._250828_spring_practice_customuserdetails.model;

import lombok.*;

@Getter
public enum Role {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN"),
    MANAGER("ROLE_MANAGER");

    private final String authority;

    Role(String authority) {
        this.authority = authority;
    }
}