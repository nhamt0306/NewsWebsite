package com.example.newswebsite.service.impl;

import com.example.newswebsite.config.LocalVariable;
import com.example.newswebsite.entity.Interactions;
import com.example.newswebsite.repository.InteractionRepository;
import com.example.newswebsite.repository.PostRepository;
import com.example.newswebsite.repository.UserRepository;
import com.example.newswebsite.service.InteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InteractionServiceImpl implements InteractionService {
    @Autowired
    InteractionRepository interactionRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;


    @Override
    public Interactions save(Interactions interactions) {
        return interactionRepository.save(interactions);
    }

    @Override
    public Interactions upVotePostByUser(Long postId, Long userId) {
        Interactions interactions = interactionRepository.findByUserEntityIdAndPostId(userId, postId);
        if(interactions == null)
        {
            interactions.setPost(postRepository.findById(postId).get());
            interactions.setUserEntity(userRepository.findById(userId).get());
            interactions.setUpVote(LocalVariable.isTrue);
            interactions.setDownVote(LocalVariable.isFalse);
            interactionRepository.save(interactions);
            return interactions;
        }
        if (interactions.getUpVote() == LocalVariable.isTrue)
        {
            return interactions;
        }else {
            interactions.setUpVote(LocalVariable.isTrue);
            interactions.setDownVote(LocalVariable.isFalse);
            interactionRepository.save(interactions);
        }
        return interactions;
    }

    @Override
    public Interactions downVotePostByUser(Long postId, Long userId) {
        Interactions interactions = interactionRepository.findByUserEntityIdAndPostId(userId, postId);
        if(interactions == null)
        {
            interactions.setPost(postRepository.findById(postId).get());
            interactions.setUserEntity(userRepository.findById(userId).get());
            interactions.setUpVote(LocalVariable.isFalse);
            interactions.setDownVote(LocalVariable.isTrue);
            interactionRepository.save(interactions);
            return interactions;
        }
        if (interactions.getUpVote() == LocalVariable.isTrue)
        {
            interactions.setUpVote(LocalVariable.isFalse);
            interactions.setDownVote(LocalVariable.isTrue);
            interactionRepository.save(interactions);
        }else {
            return interactions;
        }
        return interactions;
    }

    @Override
    public Integer countUpVoteByPost(Long postId) {
        return interactionRepository.countUpVoteByPostId(postId);
    }

    @Override
    public Integer countDownVoteByPost(Long postId) {
        return interactionRepository.countDownVoteByPostId(postId);
    }

    @Override
    public List<Interactions> getListFavoritePostByUser(Long userId) {
        return interactionRepository.findAllByUserEntityIdAndUpVote(userId, LocalVariable.isTrue);
    }

    @Override
    public Interactions findByUserAndPost(Long userId, Long postId) {
        return interactionRepository.findByUserEntityIdAndPostId(userId, postId);
    }
}
