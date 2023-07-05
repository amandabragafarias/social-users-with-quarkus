package com.social.quarkus.controller;

import com.social.quarkus.dto.FollowerRequest;
import com.social.quarkus.dto.FollowerResponse;
import com.social.quarkus.dto.PostResponse;
import com.social.quarkus.dto.ResponseError;
import com.social.quarkus.entities.Follower;
import com.social.quarkus.entities.Post;
import com.social.quarkus.entities.User;
import com.social.quarkus.repository.FollowerRepository;
import com.social.quarkus.repository.PostRepository;
import com.social.quarkus.repository.UserRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/api/users/{user_id}/followers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FollowerResource {

    private FollowerRepository followerRepository;
    private UserRepository userRepository;
    @Inject
    public FollowerResource(FollowerRepository followerRepository, UserRepository userRepository){
        this.followerRepository = followerRepository;
        this.userRepository = userRepository;
    }
    @POST
    @Path("{follower_id}")
    @Transactional
    public Response follow(@PathParam("user_id") Long userId, @PathParam("follower_id") Long followerId){
        User user = userRepository.findById(userId);
        if(user != null){
            //User newFollower = userRepository.findById(request.getFollowerId());
            Follower follower = new Follower();
            boolean follows = followerRepository.followers(user, follower.getUser());
            if(!follows){
                follower.setUser(user);
                follower.setFollower(userRepository.findById(followerId));
                followerRepository.persist(follower);
            }

        }else{
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
        return Response
                .noContent()
                .build();
    }
    @GET
    @Transactional
    public Response listFollowers(@PathParam("user_id") Long id){
      //  List<Follower> followers = followerRepository.findByUser(id);
        User user = userRepository.findById(id);
        if(user != null){
            List<Follower> followers = followerRepository.findByUser(id);

            List<FollowerResponse> followersResponseList = followers.stream()
                    .map(follower -> FollowerResponse.response(follower))
                    .collect(Collectors.toList());
            return Response
                    .ok(followersResponseList)
                    .build();

        }
        return Response.status(Response.Status.NOT_FOUND).build();



    }
    @DELETE
    @Path("{follower_id}")
    @Transactional
    public Response unfollow(@PathParam("user_id") Long userId, @PathParam("follower_id") Long followerId){
        User user = userRepository.findById(userId);
        if(user != null){
            followerRepository.unfollowUser(userId, followerId);
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();

    }


}
