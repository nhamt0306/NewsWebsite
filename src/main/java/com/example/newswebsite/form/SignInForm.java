package com.example.newswebsite.form;

import lombok.Data;

@Data
public class SignInForm {
    private String username;
    private String password;

    public SignInForm() {
    }

    public SignInForm(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
