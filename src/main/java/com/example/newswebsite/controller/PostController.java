package com.example.newswebsite.controller;

import com.example.newswebsite.config.LocalVariable;
import com.example.newswebsite.dto.post.PostDTO;
import com.example.newswebsite.dto.post.PostPagingDTO;
import com.example.newswebsite.entity.Category;
import com.example.newswebsite.entity.Interactions;
import com.example.newswebsite.entity.Post;
import com.example.newswebsite.entity.UserEntity;
import com.example.newswebsite.form.post.CreatePostForm;
import com.example.newswebsite.form.post.UpdatePostForm;
import com.example.newswebsite.mapper.PostMapper;
import com.example.newswebsite.security.principal.UserDetailService;
import com.example.newswebsite.service.impl.CategoryServiceImpl;
import com.example.newswebsite.service.impl.InteractionServiceImpl;
import com.example.newswebsite.service.impl.PostServiceImpl;
import com.example.newswebsite.service.impl.UserServiceImpl;
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
public class PostController {
    @Autowired
    PostServiceImpl postService;
    @Autowired
    PostMapper postMapper;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    UserDetailService userDetailService;
    @Autowired
    CategoryServiceImpl categoryService;
    @Autowired
    InteractionServiceImpl interactionService;

    @GetMapping("/post/get-all")
    public ResponseEntity<?> getAll()
    {
        List<Post> postList = postService.getAll();
        List<PostDTO> postDTOList = new ArrayList<>();
        for (Post post : postList)
        {
            PostDTO postDTO = postMapper.mapperPostToDTO(post);
            postDTOList.add(postDTO);
        }
        return ResponseEntity.ok(postDTOList);
    }

    // category paging
    @GetMapping("/post")
    public Object getAllWithPaging(@RequestParam(defaultValue = "1") Integer pageNo,
                                   @RequestParam(defaultValue = "100") Integer pageSize,
                                   @RequestParam(defaultValue = "id") String sortBy) {
        Integer maxPageSize;
        Integer maxPageNo;
        List<Post> postList = new ArrayList<>();

        maxPageSize = postService.findAllByStatus(LocalVariable.activeStatus).size();
        if (pageSize > maxPageSize)
        {
            pageSize = 12;
        }
        maxPageNo = maxPageSize / pageSize;
        if (pageNo > maxPageNo +1)
        {
            pageNo = maxPageNo +1;
        }
        postList = postService.getAllPaging(pageNo-1, pageSize, sortBy);

        List<PostDTO> postDTOList = new ArrayList<>();
        for (Post post : postList)
        {
            PostDTO postDTO = postMapper.mapperPostToDTO(post);
            postDTOList.add(postDTO);
        }

        PostPagingDTO postPagingDTO = new PostPagingDTO(postDTOList, maxPageSize);
        return postPagingDTO;
    }

    // get by total comment
    @GetMapping("/post/sort-by-comment")
    public Object getAllWithPagingSortByTotalComment(@RequestParam(defaultValue = "1") Integer pageNo,
                                   @RequestParam(defaultValue = "100") Integer pageSize,
                                   @RequestParam(defaultValue = "id") String sortBy) {
        Integer maxPageSize;
        Integer maxPageNo;
        List<Post> postList = new ArrayList<>();

        maxPageSize = postService.findAllByStatus(LocalVariable.activeStatus).size();
        if (pageSize > maxPageSize)
        {
            pageSize = 12;
        }
        maxPageNo = maxPageSize / pageSize;
        if (pageNo > maxPageNo +1)
        {
            pageNo = maxPageNo +1;
        }
        postList = postService.getAllPagingSortByComment(pageNo-1, pageSize, sortBy);

        List<PostDTO> postDTOList = new ArrayList<>();
        for (Post post : postList)
        {
            PostDTO postDTO = postMapper.mapperPostToDTO(post);
            postDTOList.add(postDTO);
        }

        PostPagingDTO postPagingDTO = new PostPagingDTO(postDTOList, maxPageSize);
        return postPagingDTO;
    }

    // get by total view
    @GetMapping("/post/sort-by-view")
    public Object getAllWithPagingSortByTotalView(@RequestParam(defaultValue = "1") Integer pageNo,
                                                     @RequestParam(defaultValue = "100") Integer pageSize,
                                                     @RequestParam(defaultValue = "id") String sortBy) {
        Integer maxPageSize;
        Integer maxPageNo;
        List<Post> postList = new ArrayList<>();

        maxPageSize = postService.findAllByStatus(LocalVariable.activeStatus).size();
        if (pageSize > maxPageSize)
        {
            pageSize = 12;
        }
        maxPageNo = maxPageSize / pageSize;
        if (pageNo > maxPageNo +1)
        {
            pageNo = maxPageNo +1;
        }
        postList = postService.getAllPagingSortByView(pageNo-1, pageSize, sortBy);

        List<PostDTO> postDTOList = new ArrayList<>();
        for (Post post : postList)
        {
            PostDTO postDTO = postMapper.mapperPostToDTO(post);
            postDTOList.add(postDTO);
        }

        PostPagingDTO postPagingDTO = new PostPagingDTO(postDTOList, maxPageSize);
        return postPagingDTO;
    }

    // category paging
    @GetMapping("/post/get-all-by-user/{id}")
    public Object getAllPagingByUser(@PathVariable long id,
                                     @RequestParam(defaultValue = "1") Integer pageNo,
                                   @RequestParam(defaultValue = "100") Integer pageSize,
                                   @RequestParam(defaultValue = "id") String sortBy) {
        Integer maxPageSize;
        Integer maxPageNo;
        List<Post> postList = new ArrayList<>();

        maxPageSize = postService.findAllByUserId(id).size();
        if (pageSize > maxPageSize)
        {
            pageSize = 12;
        }
        maxPageNo = maxPageSize / pageSize;
        if (pageNo > maxPageNo +1)
        {
            pageNo = maxPageNo +1;
        }
        postList = postService.getAllPagingByUser(id,pageNo-1, pageSize, sortBy);

        List<PostDTO> postDTOList = new ArrayList<>();
        for (Post post : postList)
        {
            PostDTO postDTO = postMapper.mapperPostToDTO(post);
            postDTOList.add(postDTO);
        }

        PostPagingDTO postPagingDTO = new PostPagingDTO(postDTOList, maxPageSize);
        return postPagingDTO;
    }


    // category paging
    @GetMapping("/post/get-all-by-category/{id}")
    public Object getAllPagingByCategory(@PathVariable long id,
                                     @RequestParam(defaultValue = "1") Integer pageNo,
                                     @RequestParam(defaultValue = "100") Integer pageSize,
                                     @RequestParam(defaultValue = "id") String sortBy) {
        Integer maxPageSize;
        Integer maxPageNo;
        List<Post> postList = new ArrayList<>();

        maxPageSize = postService.findAllByCategoryId(id).size();
        if (pageSize > maxPageSize)
        {
            pageSize = 12;
        }
        maxPageNo = maxPageSize / pageSize;
        if (pageNo > maxPageNo +1)
        {
            pageNo = maxPageNo +1;
        }
        postList = postService.getAllPagingByCategory(id,pageNo-1, pageSize, sortBy);

        List<PostDTO> postDTOList = new ArrayList<>();
        for (Post post : postList)
        {
            PostDTO postDTO = postMapper.mapperPostToDTO(post);
            postDTOList.add(postDTO);
        }

        PostPagingDTO postPagingDTO = new PostPagingDTO(postDTOList, maxPageSize);
        return postPagingDTO;
    }




    @GetMapping("/post/get-all-active")
    public ResponseEntity<?> getAllActive()
    {
        List<Post> postList = postService.findAllByStatus(LocalVariable.activeStatus);
        List<PostDTO> postDTOList = new ArrayList<>();
        for (Post post : postList)
        {
            PostDTO postDTO = postMapper.mapperPostToDTO(post);
            postDTOList.add(postDTO);
        }
        return ResponseEntity.ok(postDTOList);
    }

    @GetMapping("/post/get-all-by-parent-id/{id}")
    public ResponseEntity<?> getAllByParentId(@PathVariable long id)
    {
        List<Post> postList = postService.findAllByParentId(id);
        List<PostDTO> postDTOList = new ArrayList<>();
        for (Post post : postList)
        {
            PostDTO postDTO = postMapper.mapperPostToDTO(post);
            postDTOList.add(postDTO);
        }
        return ResponseEntity.ok(postDTOList);
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<?> getById(@PathVariable long id){
        Post post = postService.findById(id);
        if (post == null) {
            return new ResponseEntity<>(LocalVariable.messageCannotFindPost + id, HttpStatus.NOT_FOUND);
        }
        else {
            PostDTO postDTO = postMapper.mapperPostToDTO(post);
            post.setTotalView(post.getTotalView() + 1);
            postService.save(post);
            return ResponseEntity.ok(postDTO);
        }
    }

    @PostMapping("/user/like/{id}")
    public ResponseEntity<?> likePostById(@PathVariable long id){
        if (interactionService.findByUserAndPost(userDetailService.getCurrentUser().getId(), id) != null) {
            Interactions interactions = interactionService.findByUserAndPost(userDetailService.getCurrentUser().getId(), id);
            if (interactions.getUpVote() != LocalVariable.isTrue) {
                interactions.setUpVote(LocalVariable.isTrue);
                interactions.setDownVote(LocalVariable.isFalse);
                interactionService.save(interactions);
            }else {
                interactionService.deleteById(interactions.getId());
            }
        }else {
            Interactions interactions = new Interactions();
            interactions.setPost(postService.findById(id));
            interactions.setUpVote(LocalVariable.isTrue);
            interactions.setDownVote(LocalVariable.isFalse);
            interactions.setUserEntity(userDetailService.getCurrentUser());
            interactionService.save(interactions);
        }
        return ResponseEntity.ok("Like post success!");
    }

    @PostMapping("/user/dislike/{id}")
    public ResponseEntity<?> dislikePostById(@PathVariable long id){
        if (interactionService.findByUserAndPost(userDetailService.getCurrentUser().getId(), id) != null) {
            Interactions interactions = interactionService.findByUserAndPost(userDetailService.getCurrentUser().getId(), id);
            if (interactions.getDownVote() != LocalVariable.isTrue) {
                interactions.setDownVote(LocalVariable.isTrue);
                interactions.setUpVote(LocalVariable.isFalse);
                interactionService.save(interactions);
            }else {
                interactionService.deleteById(interactions.getId());
            }
        }else {
            Interactions interactions = new Interactions();
            interactions.setPost(postService.findById(id));
            interactions.setDownVote(LocalVariable.isTrue);
            interactions.setUpVote(LocalVariable.isFalse);
            interactions.setUserEntity(userDetailService.getCurrentUser());
            interactionService.save(interactions);
        }
        return ResponseEntity.ok("Dislike post success!");
    }

    @PostMapping("/admin/post/create")
    public Object createPost(@RequestBody CreatePostForm createPostForm) throws ParseException {
        Post post = new Post();
        post.setTitle(createPostForm.getTitle());
        post.setContent(createPostForm.getContent());
        post.setSlug(createPostForm.getSlug());
        post.setTotalComment(0L);
        post.setTotalView(0L);
        post.setCreateAt(new Date());
        post.setUpdateAt(new Date());
        if (createPostForm.getParentId() == null){
            post.setParentId(0L);
        }else {
            post.setParentId(createPostForm.getParentId());
        }
        post.setStatus(LocalVariable.activeStatus);
        post.setUserEntity(userDetailService.getCurrentUser());
        // save in table post_category
        if(createPostForm.getCategoryId() != null){
            post.setCategory(categoryService.findById(createPostForm.getCategoryId()));
        }
        postService.save(post);
        // response dto for FE
        PostDTO postDTO = postMapper.mapperPostToDTO(post);
        return postDTO;
    }

    @PostMapping("/user/post/create")
    public Object createPostByUser(@RequestBody CreatePostForm createPostForm) throws ParseException {
        Post post = new Post();
        post.setTitle(createPostForm.getTitle());
        post.setContent(createPostForm.getContent());
        post.setSlug(createPostForm.getSlug());
        post.setTotalComment(0L);
        post.setTotalView(0L);
        post.setCreateAt(new Date());
        post.setUpdateAt(new Date());
        if (createPostForm.getParentId() == null){
            post.setParentId(0L);
        }else {
            post.setParentId(createPostForm.getParentId());
        }
        post.setStatus(LocalVariable.pendingStatus);
        post.setUserEntity(userDetailService.getCurrentUser());
        // save in table post_category
        if(createPostForm.getCategoryId() != null){
            post.setCategory(categoryService.findById(createPostForm.getCategoryId()));
        }
        postService.save(post);
        // response dto for FE
        PostDTO postDTO = postMapper.mapperPostToDTO(post);
        return postDTO;
    }

    @DeleteMapping("/admin/post/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id)
    {
        postService.deleteById(id);
        return ResponseEntity.ok(LocalVariable.messageDeletePostSuccess);
    }

    @PostMapping("/admin/post/accept/{id}")
    public ResponseEntity<?> acceptPostById(@PathVariable long id)
    {
        Post post = postService.findById(id);
        if (post == null) {
            return new ResponseEntity<>(LocalVariable.messageCannotFindPost + id, HttpStatus.NOT_FOUND);
        }
        post.setStatus(LocalVariable.activeStatus);
        return ResponseEntity.ok(LocalVariable.messageUpdatePost+ id);
    }

    @PutMapping("/admin/post/update")
    public Object updatePost(@RequestBody UpdatePostForm updatePostForm) throws ParseException {
        Post post = postService.findById(updatePostForm.getId());
        if (post == null) {
            return new ResponseEntity<>(LocalVariable.messageCannotFindPost + updatePostForm.getId(), HttpStatus.NOT_FOUND);
        }
        if (updatePostForm.getTitle() != null){
            post.setTitle(updatePostForm.getTitle());
        }
        if (updatePostForm.getContent() != null){
            post.setContent(updatePostForm.getContent());
        }
        if (updatePostForm.getSlug() != null){
            post.setSlug(updatePostForm.getSlug());
        }
        if (updatePostForm.getUserId() != null){
            post.setUserEntity(userService.findById(updatePostForm.getUserId()).get());
        }
        if (updatePostForm.getParentId() != null){
            post.setParentId(updatePostForm.getParentId());
        }
        if (updatePostForm.getCategoryId() != null){
            post.setCategory(categoryService.findById(updatePostForm.getCategoryId()));
        }
        post.setUpdateAt(new Date());
        postService.save(post);
        PostDTO postDTO = postMapper.mapperPostToDTO(post);
        return postDTO;
    }


    @GetMapping("/post/search")
    public Object searchPost(@RequestParam String keyword) throws ParseException {
        List<Post> postList = postService.searchPostByKeyword(keyword);
        List<PostDTO> postDTOList = new ArrayList<>();
        for (Post post : postList)
        {
            PostDTO postDTO = postMapper.mapperPostToDTO(post);
            postDTOList.add(postDTO);
        }
        return ResponseEntity.ok(postDTOList);
    }
}
