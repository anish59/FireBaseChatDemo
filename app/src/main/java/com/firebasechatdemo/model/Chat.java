package com.firebasechatdemo.model;

public class Chat {
    private String Message;
    private String SenderId;
    private String ReceiverId;
    private String UtcTime;
    private int MediaType;
    private String Media;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getSenderId() {
        return SenderId;
    }

    public void setSenderId(String senderId) {
        SenderId = senderId;
    }

    public String getReceiverId() {
        return ReceiverId;
    }

    public void setReceiverId(String receiverId) {
        ReceiverId = receiverId;
    }

    public String getUtcTime() {
        return UtcTime;
    }

    public void setUtcTime(String utcTime) {
        UtcTime = utcTime;
    }

    public int getMediaType() {
        return MediaType;
    }

    public void setMediaType(int mediaType) {
        MediaType = mediaType;
    }

    public String getMedia() {
        return Media;
    }

    public void setMedia(String media) {
        Media = media;
    }
}
