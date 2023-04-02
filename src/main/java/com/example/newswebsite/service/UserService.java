package com.example.newswebsite.service;


import com.example.newswebsite.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserEntity> findByUsername(String username);//Tìm User thông qua username
    Boolean existsByUsername(String username); //Kiểm tra username có trong db chưa?
    Boolean existsByEmail(String email);
    UserEntity save(UserEntity user);
    void deleteById(Long id);
    Optional<UserEntity> findById(Long id);
    List<UserEntity> findAll();
    Optional<UserEntity> findByEmail(String email);//Tìm User thông qua username
    Boolean upRole(String email);
    Boolean downRole(String email);
    UserEntity uploadAvatar(MultipartFile image, Long id);
    UserEntity changeUserPasswordByEmail(String email, String newPassword);

}
