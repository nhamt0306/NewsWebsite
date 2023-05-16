package com.example.newswebsite.entity;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@Entity
@Table(name = "interactions")
public class Interactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer upVote;
    private Integer downVote;

    //relationship: many to one with post
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "post_id", nullable = false, referencedColumnName = "id") //category_id is foreign key of SubCategory and referenced to id of table Category
    private Post post;

    //relationship: many to one with user
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id") //category_id is foreign key of SubCategory and referenced to id of table Category
    private UserEntity userEntity;
}
