package com.example.newswebsite.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String slug; // ten url. vd: localhost:666/news/slug-la-gi  --> slug-la-gi chinh la slug
    private String status;


    //Ràng buộc quan hệ JPA data spring
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name= "tag_post",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id"))
    List<Post> tagPosts = new ArrayList<>();

    public List<Post> getTagPosts() {
        return tagPosts;
    }

    public void setTagPosts(List<Post> tagPosts) {
        this.tagPosts = tagPosts;
    }
}
