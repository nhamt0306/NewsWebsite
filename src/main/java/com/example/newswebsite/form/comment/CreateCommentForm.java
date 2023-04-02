package com.example.newswebsite.form.comment;

import lombok.Data;

import java.util.Date;

@Data
public class CreateCommentForm {
    private String content;
    private Long parentId;
    private Long postId;

}
