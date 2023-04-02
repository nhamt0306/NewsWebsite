package com.example.newswebsite.service.impl;

import com.example.newswebsite.config.LocalVariable;
import com.example.newswebsite.entity.Tag;
import com.example.newswebsite.repository.TagRepository;
import com.example.newswebsite.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    TagRepository tagRepository;

    @Override
    public List<Tag> getAll() {
        return tagRepository.findAll();
    }

    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag findById(Long id) {
        return tagRepository.findById(id).orElse(null);
    }

    @Override
    public List<Tag> findAllByTitle(String title) {
        return tagRepository.findAllByTitle(title);
    }

    @Override
    public List<Tag> findAllByStatus(String status) {
        return tagRepository.findAllByStatus(LocalVariable.activeStatus);
    }

    @Override
    public void deleteById(Long id) {
        Tag tag = tagRepository.findById(id).get();
        if (tag.getStatus().equals(LocalVariable.activeStatus)) {
            tag.setStatus(LocalVariable.disableStatus);
        }
        else {
            tag.setStatus(LocalVariable.activeStatus);
        }
        tagRepository.save(tag);
    }

    @Override
    public List<Tag> getAllPaging(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Tag> pagedResult = tagRepository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Tag>();
        }
    }
}
