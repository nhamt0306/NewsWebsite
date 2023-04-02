package com.example.newswebsite.dto.category;

import lombok.Data;

import java.util.List;

@Data
public class CategoryPagingDTO {
    private List<CategoryDTO> categoryDTOList;
    private Integer maxPageSize;

    public CategoryPagingDTO(List<CategoryDTO> categoryDTOS, Integer maxPageSize) {
        this.categoryDTOList = categoryDTOS;
        this.maxPageSize = maxPageSize;
    }
}
