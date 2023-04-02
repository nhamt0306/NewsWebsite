package com.example.newswebsite.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String slug; // ten url. vd: localhost:666/news/slug-la-gi  --> slug-la-gi chinh la slug
    private Long parentId;
    private String status;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name= "category_post",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id"))
    List<Post> categoryPosts = new ArrayList<>();

    public List<Post> getCategoryPosts() {
        return categoryPosts;
    }

    public void setCategoryPosts(List<Post> categoryPosts) {
        this.categoryPosts = categoryPosts;
    }
}
