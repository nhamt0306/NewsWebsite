package com.example.newswebsite.form.tag;

import lombok.Data;

@Data
public class CreateTagForm {
    private String title;
    private String content;
    private String slug;
}
