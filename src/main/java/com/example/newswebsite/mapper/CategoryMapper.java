package com.example.newswebsite.mapper;

import com.example.newswebsite.dto.category.CategoryDTO;
import com.example.newswebsite.entity.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {
    public CategoryDTO mapperCategoryToDTO(Category category){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setTitle(category.getTitle());
        categoryDTO.setContent(category.getContent());
        categoryDTO.setStatus(category.getStatus());
        categoryDTO.setParentId(category.getParentId());
        categoryDTO.setSlug(category.getSlug());
        return categoryDTO;
    }
}
