package com.example.i170212_i170321;

import android.widget.ImageView;

public class Message {
    private String content;
    public String sender;
    public ImageView photo;
    public boolean isPhoto;
    private boolean liked;
    private String time;
    private boolean read;

    public Message()
    {
        this.content = "";
        this.time = "";
        read = false;
        liked = false;
        isPhoto = false;
    }
    public Message(String content, String time, boolean read) {
        this.content = content;
        this.time = time;
        this.read = read;
        liked = false;
        isPhoto = false;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public ImageView getPhoto() {
        return photo;
    }

    public void setPhoto(ImageView photo) {
        this.photo = photo;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
