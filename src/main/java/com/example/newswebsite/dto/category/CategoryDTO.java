package com.example.newswebsite.dto.category;

import lombok.Data;

@Data
public class CategoryDTO {
    private Long id;
    private String title;
    private String content;
    private String slug;
    private Long parentId;
    private String status;
}
