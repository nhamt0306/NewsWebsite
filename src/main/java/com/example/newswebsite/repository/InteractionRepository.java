package com.example.newswebsite.repository;

import com.example.newswebsite.entity.Interactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InteractionRepository extends JpaRepository<Interactions, Long> {
    @Query(nativeQuery = true,value = "    SELECT count(*) \n" +
            "    FROM news_website.interactions \n" +
            "    WHERE post_id = :PostID and down_vote = 1 ")
    Integer countDownVoteByPostId(@Param("PostID") Long PostID);

    @Query(nativeQuery = true,value = "    SELECT count(*) \n" +
            "    FROM news_website.interactions \n" +
            "    WHERE post_id = :PostID and up_vote = 1 ")
    Integer countUpVoteByPostId(@Param("PostID") Long PostID);

    List<Interactions> findAllByUserEntityIdAndUpVote(Long userId, Integer upVote);

    Interactions findByUserEntityIdAndPostId(Long userId, Long postId);
}
