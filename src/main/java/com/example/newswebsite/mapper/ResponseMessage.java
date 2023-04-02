package com.example.newswebsite.mapper;

public class ResponseMessage {
    private String message;
    private String success;

    public ResponseMessage() {

    }

    public ResponseMessage(String message, String success) {
        this.message = message;
        this.success = success;
    }

    public ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
