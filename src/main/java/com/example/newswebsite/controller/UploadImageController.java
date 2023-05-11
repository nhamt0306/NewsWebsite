package com.example.newswebsite.controller;

import com.example.newswebsite.entity.Post;
import com.example.newswebsite.entity.UserEntity;
import com.example.newswebsite.security.principal.UserDetailService;
import com.example.newswebsite.service.impl.PostServiceImpl;
import com.example.newswebsite.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UploadImageController {
    @Autowired
    PostServiceImpl postService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    UserDetailService userDetailService;

    @PostMapping("/admin/post/{id}/image")
    public ResponseEntity<?> uploadProductImg(@PathVariable long id,
                                              @RequestParam(value = "image", required = false) MultipartFile image) {
        if(!image.getContentType().equals("image/png") && !image.getContentType().equals("image/jpeg")) {
            return new ResponseEntity<>("File khong hop le!", HttpStatus.BAD_REQUEST);
        }
        Post post = postService.uploadImage(id, image);

        return ResponseEntity.ok("Upload image success:" + post.getThumbnail());
    }

    @PostMapping("/user/profile/avatar")
    public ResponseEntity<?> uploadAvatarImg(@RequestParam(value = "image", required = false) MultipartFile image) {
        UserEntity user = userDetailService.getCurrentUser();
        if(!image.getContentType().equals("image/png") && !image.getContentType().equals("image/jpeg") && !image.getContentType().equals("image/jpg")) {
            return new ResponseEntity<>("File khong hop le!", HttpStatus.BAD_REQUEST);
        }

        userService.uploadAvatar(image, user.getId());
        return ResponseEntity.ok("upload avatar success!");
    }
}
