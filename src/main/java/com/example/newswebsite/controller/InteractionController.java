package com.example.newswebsite.controller;

import com.example.newswebsite.config.LocalVariable;
import com.example.newswebsite.dto.comment.CommentDTO;
import com.example.newswebsite.dto.post.PostDTO;
import com.example.newswebsite.entity.Comment;
import com.example.newswebsite.entity.Interactions;
import com.example.newswebsite.entity.Post;
import com.example.newswebsite.form.comment.CreateCommentForm;
import com.example.newswebsite.mapper.PostMapper;
import com.example.newswebsite.security.principal.UserDetailService;
import com.example.newswebsite.service.impl.InteractionServiceImpl;
import com.example.newswebsite.service.impl.PostServiceImpl;
import com.example.newswebsite.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping
@RestController
public class InteractionController {
    @Autowired
    InteractionServiceImpl interactionService;
    @Autowired
    UserDetailService userDetailService;
    @Autowired
    PostServiceImpl postService;
    @Autowired
    PostMapper postMapper;

    @GetMapping("/interaction/get-favorite-post-by-user")
    public ResponseEntity<?> getAllFavoritePostByUserId()
    {
        List<Interactions> interactions = interactionService.getListFavoritePostByUser(userDetailService.getCurrentUser().getId());
        List<PostDTO> postDTOList = new ArrayList<>();
        for (Interactions interactionsItem : interactions)
        {
            if (interactionsItem.getUpVote() == 1)
            {
                Post post = postService.findById(interactionsItem.getPost().getId());
                PostDTO postDTO = postMapper.mapperPostToDTO(post);
                postDTO.setNumberVote(interactionService.countUpVoteByPost(post.getId()));
                postDTO.setIsVoted(LocalVariable.isTrue);
                postDTOList.add(postDTO);
            }
        }
        return ResponseEntity.ok(postDTOList);
    }


    @PostMapping("/user/interactions/like")
    public Object likePost(@RequestBody Long postId) throws ParseException {
        interactionService.upVotePostByUser(postId, userDetailService.getCurrentUser().getId());
        return "like successfully";
    }

    @PostMapping("/user/interactions/dis-like")
    public Object dislikePost(@RequestBody Long postId) throws ParseException {
        interactionService.downVotePostByUser(postId, userDetailService.getCurrentUser().getId());
        return "like successfully";
    }
}
