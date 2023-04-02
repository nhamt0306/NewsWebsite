package com.example.newswebsite.form.category;

import lombok.Data;

@Data
public class UpdateCategoryForm {
    private Long id;
    private String title;
    private String content;
    private String slug;
    private Long parentId;
}
