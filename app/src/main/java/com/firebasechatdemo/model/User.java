package com.firebasechatdemo.model;

import java.io.Serializable;

public class User implements Serializable {
    private String Id;
    private String UserName;
    private String ImageUrl;

    public User(String id, String userName, String imageUrl) {
        Id = id;
        UserName = userName;
        ImageUrl = imageUrl;
    }

    public User() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "User{" +
                "Id='" + Id + '\'' +
                ", UserName='" + UserName + '\'' +
                ", ImageUrl='" + ImageUrl + '\'' +
                '}';
    }
}
