package com.social.quarkus.dto;

import com.social.quarkus.entities.Follower;

import java.util.Objects;

public class FollowerResponse {
    private Long id;
    private String name;

    public static FollowerResponse response(Follower follower){
        var response = new FollowerResponse();
        response.setId(follower.getFollower().getId());
        response.setName(follower.getFollower().getName());
        return response;
    }
    public FollowerResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public FollowerResponse(Follower follower) {
        this(follower.getId(), follower.getFollower().getName());
    }
    public FollowerResponse() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FollowerResponse that = (FollowerResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
