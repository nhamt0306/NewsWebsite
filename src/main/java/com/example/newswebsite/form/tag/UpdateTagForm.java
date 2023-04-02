package com.example.newswebsite.form.tag;

import lombok.Data;

@Data
public class UpdateTagForm {
    private Long id;
    private String title;
    private String content;
    private String slug;
}
