package com.example.newswebsite.service;

import com.example.newswebsite.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();
    Category save(Category category);
    Category findById(Long id);
    List<Category> findAllCategoryByTitle(String title);
    List<Category> findByCategoryByParentId(Long id);
    List<Category> findAllByStatus(String status);
    void deleteById(Long id);
    List<Category> getAllCategoryPaging(Integer pageNo, Integer pageSize);
}
