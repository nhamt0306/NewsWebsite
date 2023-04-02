package com.example.newswebsite.dto.user;

import lombok.Data;

import java.util.Set;

@Data
public class RegisterWithOauth {
    private String name;
    private String email;
    private String password;
    private Set<String> roles;
}
