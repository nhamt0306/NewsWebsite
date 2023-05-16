package com.example.newswebsite.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private Date createAt;
    private Date updateAt;
    private String slug;
    private Long parentId;
    private String status;
    private String thumbnail;
    private Long totalView;
    private Long totalComment;


    //Relationship
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Comment> comments = new ArrayList<>();

    //relationship: many to one with user
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private UserEntity userEntity;

    //Relationship
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Interactions> interactions = new ArrayList<>();

    //relationship: many to one with user
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "category_id", nullable = false, referencedColumnName = "id")
    private Category category;

}
