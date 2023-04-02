package com.example.newswebsite.dto.tag;

import lombok.Data;

import java.util.List;
@Data
public class TagPagingDTO {
    private List<TagDTO> tagDTOList;
    private Integer maxPageSize;

    public TagPagingDTO(List<TagDTO> tagDTOList, Integer maxPageSize) {
        this.tagDTOList = tagDTOList;
        this.maxPageSize = maxPageSize;
    }
}
