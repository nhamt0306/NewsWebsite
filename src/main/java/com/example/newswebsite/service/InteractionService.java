package com.example.newswebsite.service;

import com.example.newswebsite.entity.Interactions;

import java.util.List;

public interface InteractionService {
    Interactions save(Interactions interactions);
    Interactions upVotePostByUser(Long postId, Long userId);
    Interactions downVotePostByUser(Long postId, Long userId);
    Integer countUpVoteByPost(Long postId);
    Integer countDownVoteByPost(Long postId);
    List<Interactions> getListFavoritePostByUser(Long userId);
    Interactions findByUserAndPost(Long userId, Long postId);
    void deleteById(Long id);
}
