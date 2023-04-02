package com.example.newswebsite.dto.post;

import lombok.Data;

import java.util.List;

@Data
public class PostPagingDTO {
    private List<PostDTO> postDTOList;
    private Integer maxPageSize;

    public PostPagingDTO(List<PostDTO> postDTOList, Integer maxPageSize) {
        this.postDTOList = postDTOList;
        this.maxPageSize = maxPageSize;
    }
}
