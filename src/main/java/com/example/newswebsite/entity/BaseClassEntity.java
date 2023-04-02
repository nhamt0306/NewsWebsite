package com.example.newswebsite.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

@MappedSuperclass
public class BaseClassEntity {
    @JsonFormat(timezone="Asia/Jakarta", pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp create_at;
    @JsonFormat(timezone="Asia/Jakarta", pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp update_at;

    public Timestamp getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Timestamp create_at) {
        this.create_at = create_at;
    }

    public Timestamp getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(Timestamp update_at) {
        this.update_at = update_at;
    }
}
