package com.example.newswebsite.dto.response;

public class BadRequest extends RuntimeException{
    public BadRequest(String message){
        super(message);
    }
}
