package com.example.newswebsite.dto.comment;

import lombok.Data;

import java.util.Date;

@Data
public class CommentDTO {
    private Long id;
    private String content;
    private Date createAt;
    private Date updateAt;
    private Long parentId;
    private Long userId;
    private String username;
    private String avatar;
}
