package com.social.quarkus.dto;


import com.social.quarkus.entities.Follower;
import com.social.quarkus.entities.Post;

public class FollowerRequest {
    private Long followerId;

    public FollowerRequest(Long followerId) {
        this.followerId = followerId;
    }

    public FollowerRequest() {
        super();
    }

    public Long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }
}
