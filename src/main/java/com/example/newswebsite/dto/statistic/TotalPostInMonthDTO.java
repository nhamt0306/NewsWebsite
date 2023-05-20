package com.example.newswebsite.dto.statistic;

import lombok.Data;

@Data
public class TotalPostInMonthDTO {
    private String time;
    private Integer amount;

    public TotalPostInMonthDTO(String time, Integer amount) {
        this.time = time;
        this.amount = amount;
    }
}
