package com.example.newswebsite.dto.tag;

import lombok.Data;

@Data
public class TagDTO {
    private Long id;
    private String title;
    private String content;
    private String slug;
    private String status;
}
