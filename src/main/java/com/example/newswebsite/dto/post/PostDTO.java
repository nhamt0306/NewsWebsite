package com.example.newswebsite.dto.post;

import lombok.Data;

@Data
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private String slug;
    private String status;
    private Long parentId;
    private Long userId;
    private Integer numberVote;
    private Integer isVoted;
    private String userName;
    private String avatar;
    private String thumbnail;
    private String categoryName;
    private Long categoryId;
}
