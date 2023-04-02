package com.example.newswebsite.repository;

import com.example.newswebsite.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findAllByTitle(String title);
    List<Tag> findAllByStatus(String status);
}
