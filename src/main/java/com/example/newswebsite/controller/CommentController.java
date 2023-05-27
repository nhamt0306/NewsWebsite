package com.example.newswebsite.controller;

import com.example.newswebsite.config.LocalVariable;
import com.example.newswebsite.dto.comment.CommentDTO;
import com.example.newswebsite.entity.Comment;
import com.example.newswebsite.entity.Post;
import com.example.newswebsite.form.comment.CreateCommentForm;
import com.example.newswebsite.form.comment.EditCommentForm;
import com.example.newswebsite.mapper.CommentMapper;
import com.example.newswebsite.security.principal.UserDetailService;
import com.example.newswebsite.service.impl.CommentServiceImpl;
import com.example.newswebsite.service.impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping
@RestController
public class CommentController {
    @Autowired
    CommentServiceImpl commentService;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    UserDetailService userDetailService;
    @Autowired
    PostServiceImpl postService;


    @GetMapping("/comment/get-all-by-post-id/{id}")
    public ResponseEntity<?> getAllByPostId(@PathVariable long id)
    {
        List<Comment> commentList = commentService.findAllByPostId(id);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (Comment comment : commentList)
        {
            CommentDTO commentDTO = commentMapper.mapperCommentToDTO(comment);
            commentDTOList.add(commentDTO);
        }
        return ResponseEntity.ok(commentDTOList);
    }


    @GetMapping("/comment/get-all-by-parent-id/{id}")
    public ResponseEntity<?> getByParentId(@PathVariable long id){
        List<Comment> commentList = commentService.findAllByParentId(id);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (Comment comment : commentList)
        {
            CommentDTO commentDTO = commentMapper.mapperCommentToDTO(comment);
            commentDTOList.add(commentDTO);
        }
        return ResponseEntity.ok(commentDTOList);
    }

    @PostMapping("/user/comment/create")
    public Object createComment(@RequestBody CreateCommentForm createCommentForm) throws ParseException {
        if(postService.findById(createCommentForm.getPostId()) == null){
            return ResponseEntity.ok("Post is not exist!");
        }
        Post post = postService.findById(createCommentForm.getPostId());
        Comment comment = new Comment();
        comment.setContent(createCommentForm.getContent());
        comment.setParentId(createCommentForm.getParentId());
        comment.setUserId(userDetailService.getCurrentUser().getId());
        comment.setParentId(createCommentForm.getParentId());
        comment.setPost(post);
        comment.setCreatedAt(new Date());
        commentService.save(comment);
        post.setTotalComment(post.getTotalComment()+ 1);
        postService.save(post);
        // response dto for FE
        CommentDTO commentDTO = commentMapper.mapperCommentToDTO(comment);
        return commentDTO;
    }

    @DeleteMapping("/user/comment/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id)
    {
        if(!commentService.existsByUserIdAndId(userDetailService.getCurrentUser().getId(), id))
        {
            return ResponseEntity.ok(LocalVariable.messageDoesNotHaveComment);
        }
        Comment comment = commentService.findById(id);
        Post post = postService.findById(comment.getPost().getId());
        post.setTotalComment(post.getTotalComment() - 1);
        commentService.deleteById(id);
        postService.save(post);
        return ResponseEntity.ok(LocalVariable.messageDeleteCommentSuccess);
    }

    @PutMapping("/user/comment/edit")
    public Object editComment(@RequestBody EditCommentForm editCommentForm) throws ParseException {
        // check comment thuoc user khong?
        if(!commentService.existsByUserIdAndId(userDetailService.getCurrentUser().getId(), editCommentForm.getId()))
        {
            return ResponseEntity.ok(LocalVariable.messageDoesNotHaveComment);
        }
        // edit comment
        Comment comment = commentService.findById(editCommentForm.getId());
        if (comment == null) {
            return new ResponseEntity<>(LocalVariable.messageCannotFindComment + editCommentForm.getId(), HttpStatus.NOT_FOUND);
        }
        if (editCommentForm.getContent() != null){
            comment.setContent(editCommentForm.getContent());
        }
        comment.setUpdateAt(new Date());

        commentService.save(comment);
        CommentDTO commentDTO = commentMapper.mapperCommentToDTO(comment);
        return commentDTO;
    }
}
