package com.example.newswebsite.form.post;

import lombok.Data;

@Data
public class CreatePostForm {
    private String title;
    private String content;
    private String slug;
    private Long parentId;
    private Long categoryId;
}
