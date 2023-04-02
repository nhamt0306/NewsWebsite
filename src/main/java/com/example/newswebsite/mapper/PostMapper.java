package com.example.newswebsite.mapper;

import com.example.newswebsite.dto.post.PostDTO;
import com.example.newswebsite.entity.Post;
import org.springframework.stereotype.Service;

@Service
public class PostMapper {
    public PostDTO mapperPostToDTO(Post post){
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());
        postDTO.setStatus(post.getStatus());
        postDTO.setParentId(post.getParentId());
        postDTO.setSlug(post.getSlug());
        postDTO.setUserId(post.getUserEntity().getId());
        return postDTO;
    }
}
