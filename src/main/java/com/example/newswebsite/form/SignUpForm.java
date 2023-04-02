package com.example.newswebsite.form;

import lombok.Data;

import java.util.Set;
@Data
public class SignUpForm {
    private String name;
    private String username;
    private String phonenumber;
    private String email;
    private String password;
    private Set<String> roles;


    public SignUpForm() {
    }

    public SignUpForm(String name, String username, String phonenumber, String email, String password, Set<String> roles) {
        this.name = name;
        this.username = username;
        this.phonenumber = phonenumber;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
}
