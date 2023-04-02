package com.example.newswebsite.mapper;

import com.example.newswebsite.dto.tag.TagDTO;
import com.example.newswebsite.entity.Tag;
import org.springframework.stereotype.Service;

@Service
public class TagMapper {
    public TagDTO mapperTagToDTO(Tag tag){
        TagDTO tagDTO = new TagDTO();
        tagDTO.setId(tag.getId());
        tagDTO.setTitle(tag.getTitle());
        tagDTO.setContent(tag.getContent());
        tagDTO.setStatus(tag.getStatus());
        tagDTO.setSlug(tag.getSlug());
        return tagDTO;
    }
}
