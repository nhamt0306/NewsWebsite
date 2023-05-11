package com.example.newswebsite.service;

import com.example.newswebsite.entity.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    List<Post> getAll();
    Post save(Post post);
    Post findById(Long id);
    List<Post> findAllByParentId(Long id);
    List<Post> findAllByStatus(String status);
    void deleteById(Long id);
    List<Post> getAllPaging(Integer pageNo, Integer pageSize);
    Post findByTile(String title);
    List<Post> findAllByUserId(Long userId);
    List<Post> getAllPagingByUser(Long userId, Integer pageNo, Integer pageSize, String sortBy);
    Post uploadImage(long id, MultipartFile image);
}
