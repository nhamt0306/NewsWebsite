package com.example.newswebsite.service.impl;

import com.example.newswebsite.entity.Comment;
import com.example.newswebsite.repository.CommentRepository;
import com.example.newswebsite.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Override
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Comment> findAllByParentId(Long parentId) {
        return commentRepository.findAllByParentId(parentId);
    }

    @Override
    public List<Comment> findAllByPostId(Long postId) {
        return commentRepository.findAllByPostId(postId);
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Boolean existsByUserIdAndId(Long userId, Long commentId) {
        return commentRepository.existsByUserIdAndId(userId,commentId);
    }
}
