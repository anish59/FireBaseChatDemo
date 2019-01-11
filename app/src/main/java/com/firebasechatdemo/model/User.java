package com.firebasechatdemo.model;

public class User {
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
}
