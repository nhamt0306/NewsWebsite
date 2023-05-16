package com.example.newswebsite.mapper;

import com.example.newswebsite.dto.category.CategoryDTO;
import com.example.newswebsite.dto.comment.CommentDTO;
import com.example.newswebsite.entity.Comment;
import com.example.newswebsite.entity.UserEntity;
import com.example.newswebsite.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentMapper {
    @Autowired
    UserServiceImpl userService;
    public CommentDTO mapperCommentToDTO(Comment comment){
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setContent(comment.getContent());
        commentDTO.setCreateAt(comment.getCreatedAt());
        commentDTO.setUpdateAt(comment.getUpdateAt());
        commentDTO.setParentId(comment.getParentId());
        commentDTO.setCommentDate(comment.getCreatedAt());
        commentDTO.setUserId(comment.getUserId());
        UserEntity user = userService.findById(comment.getUserId()).get();
        commentDTO.setUsername(user.getFullname());
        commentDTO.setAvatar(user.getAvatar());
        return commentDTO;
    }
}
