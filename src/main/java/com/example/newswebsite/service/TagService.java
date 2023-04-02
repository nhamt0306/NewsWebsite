package com.example.newswebsite.service;

import com.example.newswebsite.entity.Tag;

import java.util.List;

public interface TagService {
    List<Tag> getAll();
    Tag save(Tag tag);
    Tag findById(Long id);
    List<Tag> findAllByTitle(String title);
    List<Tag> findAllByStatus(String status);
    void deleteById(Long id);
    List<Tag> getAllPaging(Integer pageNo, Integer pageSize);
}
