package com.social.quarkus.dto;

import com.social.quarkus.entities.Post;

import java.time.LocalDateTime;
public class PostResponse {
    private String text;
    private LocalDateTime dateTime;

    public static PostResponse response(Post post){
        var response = new PostResponse();
        response.setText(post.getText());
        response.setDateTime(post.getDateTime());
        return response;
    }

    public PostResponse(String text, LocalDateTime dateTime) {
        this.text = text;
        this.dateTime = dateTime;
    }
    public PostResponse() {
        super();
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
