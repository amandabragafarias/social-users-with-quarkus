 package com.social.quarkus.entities;

import jakarta.persistence.*;

import java.util.Objects;

 @Entity
@Table(name = "tb_follower")
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long follow_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower;


    public Follower(Long follow_id, User user, User follower) {
        this.follow_id = follow_id;
        this.user = user;
        this.follower = follower;
    }
    public Follower() {
        super();
    }

    public Long getId() {
        return follow_id;
    }

    public void setId(Long follow_id) {
        this.follow_id = follow_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

     @Override
     public boolean equals(Object o) {
         if (this == o) return true;
         if (o == null || getClass() != o.getClass()) return false;
         Follower follower1 = (Follower) o;
         return Objects.equals(follow_id, follower1.follow_id) && Objects.equals(user, follower1.user) && Objects.equals(follower, follower1.follower);
     }

     @Override
     public int hashCode() {
         return Objects.hash(follow_id, user, follower);
     }
 }

