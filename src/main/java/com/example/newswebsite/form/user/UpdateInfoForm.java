package com.example.newswebsite.form.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;


@Data
public class UpdateInfoForm {
    private String address;
    @JsonFormat(timezone="Asia/Jakarta", pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp dob;
    private String fullname;
    private String gender;
    private String phone;

}
