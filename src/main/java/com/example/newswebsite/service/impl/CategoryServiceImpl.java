package com.example.newswebsite.service.impl;

import com.example.newswebsite.config.LocalVariable;
import com.example.newswebsite.entity.Category;
import com.example.newswebsite.repository.CategoryRepository;
import com.example.newswebsite.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;


    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public List<Category> findAllCategoryByTitle(String title) {
        return categoryRepository.findAllByTitle(title);
    }

    @Override
    public List<Category> findByCategoryByParentId(Long id) {
        return categoryRepository.findAllByParentId(id);
    }

    @Override
    public List<Category> findAllByStatus(String status) {
        return categoryRepository.findAllByStatus(LocalVariable.activeStatus);
    }

    @Override
    public void deleteById(Long id) {
        Category categoryEntity = categoryRepository.findById(id).get();
        if (categoryEntity.getStatus().equals(LocalVariable.activeStatus)) {
            categoryEntity.setStatus(LocalVariable.disableStatus);
        }
        else {
            categoryEntity.setStatus(LocalVariable.activeStatus);
        }
        categoryRepository.save(categoryEntity);
    }

    @Override
    public List<Category> getAllCategoryPaging(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Category> pagedResult = categoryRepository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Category>();
        }
    }
}
