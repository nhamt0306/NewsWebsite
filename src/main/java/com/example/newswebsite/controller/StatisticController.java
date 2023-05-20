package com.example.newswebsite.controller;

import com.example.newswebsite.dto.statistic.StatisticDTO;
import com.example.newswebsite.dto.statistic.TotalPostInMonthDTO;
import com.example.newswebsite.service.impl.CategoryServiceImpl;
import com.example.newswebsite.service.impl.PostServiceImpl;
import com.example.newswebsite.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@CrossOrigin(origins = "*")
@RequestMapping
@RestController
public class StatisticController {
    @Autowired
    PostServiceImpl postService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    CategoryServiceImpl categoryService;


    @GetMapping("/admin/statistic")
    public ResponseEntity<?> getAllStatistic()
    {
        Integer postList = postService.getAll().size();
        Integer userList = userService.findAll().size();
        Integer category = categoryService.getAll().size();

        StatisticDTO statisticDTO = new StatisticDTO();
        statisticDTO.setTotalPost(postList);
        statisticDTO.setTotalUser(userList);
        statisticDTO.setTotalCategory(category);
        return ResponseEntity.ok(statisticDTO);
    }
    @GetMapping("/admin/chart")
    public ResponseEntity<?> getChart()
    {
        List<TotalPostInMonthDTO> chartList= new ArrayList<>(); // map return
        for (int i =1; i< 13; i++)
        {
            Integer numberPost = Integer.parseInt(String.valueOf(postService.countPostByMonth(i)));
            TotalPostInMonthDTO statisticMapper = new TotalPostInMonthDTO(String.valueOf(i),numberPost);
            chartList.add(statisticMapper);
        }
        return ResponseEntity.ok(chartList);
    }

}
