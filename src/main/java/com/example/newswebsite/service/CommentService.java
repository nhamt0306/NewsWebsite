package com.example.newswebsite.service;

import com.example.newswebsite.entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getAll();
    Comment save(Comment comment);
    Comment findById(Long id);
    List<Comment> findAllByParentId(Long parentId);
    List<Comment> findAllByPostId(Long postId);
    void deleteById(Long id);
    Boolean existsByUserIdAndId(Long userId, Long commentId);
//    List<Comment> getAllCategoryPaging(Integer pageNo, Integer pageSize);
}
