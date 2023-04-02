package com.example.newswebsite.repository;

import com.example.newswebsite.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByParentId(Long id);
    List<Post> findAllByStatus(String status);
    Post findByTitle(String title);
    List<Post> findAllByUserEntityId(Long userId);
    Page<Post> findAllByUserEntityId(Long userId, Pageable pageable);
}
