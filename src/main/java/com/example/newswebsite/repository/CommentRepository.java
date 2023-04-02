package com.example.newswebsite.repository;

import com.example.newswebsite.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Boolean existsByUserIdAndId(Long userId, Long commentId);
    List<Comment> findAllByParentId(Long parentId);
    List<Comment> findAllByPostId(Long portId);
}
