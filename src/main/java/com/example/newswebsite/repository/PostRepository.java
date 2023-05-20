package com.example.newswebsite.repository;

import com.example.newswebsite.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByParentId(Long id);
    List<Post> findAllByStatus(String status);
    Post findByTitle(String title);
    List<Post> findAllByUserEntityId(Long userId);
    Page<Post> findAllByUserEntityId(Long userId, Pageable pageable);

    List<Post> findAllByCategoryId(Long categoryId);
    Page<Post> findAllByCategoryId(Long categoryId, Pageable pageable);

    @Query(nativeQuery = true,value = "SELECT * \n" +
            "    FROM news_website.posts \n" +
            "    WHERE posts.content like %:Keyword% or posts.title like %:Keyword%")
    List<Post> searchPost(@Param("Keyword") String Keyword);


    // Statistic by day
    @Query(value = "SELECT Count(*) FROM news_website.posts Where status = 'Active' and year(update_at) = YEAR(CURDATE()) and month(update_at) = :Month", nativeQuery = true)
    Object getTotalPostByMonth(@Param("Month") Integer Month);
}
