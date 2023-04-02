package com.example.newswebsite.config;

import java.util.Random;

public class LocalVariable {
    public static final String disableStatus = "Disable";
    public static final String activeStatus = "Active";
    public static final int OrderPagingLimit = 9000;
    public static String GetOTP() {
        return String.format("%06d", new Random().nextInt(999999));
    }
    //Category message
    public static final String messageDeleteCatSuccess = "Delete category success!";
    public static final String messageCannotFindCat = "Cannot find category with id = ";

    //Tag message
    public static final String messageDeleteTagSuccess = "Delete tag success!";
    public static final String messageCannotFindTag = "Cannot find tag with id = ";

    //Comment message
    public static final String messageDeleteCommentSuccess = "Delete comment success!";
    public static final String messageCannotFindComment = "Cannot find comment with id = ";
    public static final String messageDoesNotHaveComment = "User doesn't have this comment!";


    //Post message
    public static final String messageDeletePostSuccess = "Delete post success!";
    public static final String messageCannotFindPost = "Cannot post tag with id = ";

    // User
    public static final String messageCannotFindUser = "Cannot find user with id = ";
    //Status deliver message
    public static final String cancelMessage = "CANCELED"; // Đã hủy
    public static final String doneMessage = "DONE"; // Hoàn tất
    public static final String pendingMessage = "PENDING"; // Đợi thanh toán
    public static final String deliveringMessage = "DELIVERING"; // Đã thanh toán

    public static final String ADMIN = "Admin";

    public static final String USER = "User";


}
