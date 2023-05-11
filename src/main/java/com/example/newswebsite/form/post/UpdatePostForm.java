package com.example.newswebsite.form.post;

import lombok.Data;

@Data
public class UpdatePostForm {
    private Long id;
    private String title;
    private String content;
    private String slug;
    private Long userId;
    private Long parentId;
    private Long categoryId;
}
