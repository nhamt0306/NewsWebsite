package com.example.newswebsite.dto.user;

public class ChangePasswordDTO {
    private String curPassword;
    private String newPassword;
    private String rePassword;

    public ChangePasswordDTO(String curPassword, String newPassword, String rePassword) {
        this.curPassword = curPassword;
        this.newPassword = newPassword;
        this.rePassword = rePassword;
    }

    public String getCurPassword() {
        return curPassword;
    }

    public void setCurPassword(String curPassword) {
        this.curPassword = curPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }
}
