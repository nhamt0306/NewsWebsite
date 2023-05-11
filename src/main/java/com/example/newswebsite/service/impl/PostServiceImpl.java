package com.example.newswebsite.service.impl;

import com.example.newswebsite.config.LocalVariable;
import com.example.newswebsite.entity.Post;
import com.example.newswebsite.repository.PostRepository;
import com.example.newswebsite.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
@Service
public class PostServiceImpl implements PostService {
    @Autowired
    PostRepository postRepository;
    @Autowired
    CloudinaryService cloudinaryService;

    @Override
    public List<Post> getAll() {
        return postRepository.findAll();
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id).orElse(null);
    }


    @Override
    public List<Post> findAllByParentId(Long id) {
        return postRepository.findAllByParentId(id);
    }

    @Override
    public List<Post> findAllByStatus(String status) {
        return postRepository.findAllByStatus(status);
    }

    @Override
    public void deleteById(Long id) {
        Post post = postRepository.findById(id).get();
        if (post.getStatus().equals(LocalVariable.activeStatus)) {
            post.setStatus(LocalVariable.disableStatus);
        }
        else {
            post.setStatus(LocalVariable.activeStatus);
        }
        postRepository.save(post);
    }

    @Override
    public List<Post> getAllPaging(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize,  Sort.by(Sort.Direction.DESC, sortBy));
        Page<Post> pagedResult = postRepository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Post>();
        }
    }

    @Override
    public Post findByTile(String title) {
        return postRepository.findByTitle(title);
    }

    @Override
    public List<Post> findAllByUserId(Long userId) {
        return postRepository.findAllByUserEntityId(userId);
    }

    @Override
    public List<Post> getAllPagingByUser(Long userId, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, sortBy));
        Page<Post> pagedResult = postRepository.findAllByUserEntityId(userId, paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Post>();
        }
    }

    @Override
    public List<Post> findAllByCategoryId(Long categoryId) {
        return postRepository.findAllByCategoryId(categoryId);
    }

    @Override
    public List<Post> getAllPagingByCategory(Long categoryId, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, sortBy));
        Page<Post> pagedResult = postRepository.findAllByCategoryId(categoryId, paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Post>();
        }
    }

    @Override
    public Post uploadImage(long id, MultipartFile image) {
        Post post = postRepository.findById(id).get();
        String imageUrl = cloudinaryService.uploadFile(image,String.valueOf(id),
                "NewsWebsite"+ "/" + "Post");
        if(!imageUrl.equals("-1")) {
            post.setThumbnail(imageUrl);
        }
        else if(post.getThumbnail().equals("") || post.getThumbnail().equals("-1"))
            post.setThumbnail("");

        return postRepository.save(post);
    }


}
