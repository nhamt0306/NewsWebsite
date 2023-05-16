package com.example.newswebsite.dto.post;

import lombok.Data;

import java.util.Date;

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
    private Integer numberDislike;
    private Integer isVoted;
    private Integer isDisliked;
    private String userName;
    private String avatar;
    private String thumbnail;
    private String categoryName;
    private Long categoryId;
    private Date postDate;
    private Long totalView;
    private Long totalComment;
}
