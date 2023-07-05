package com.social.quarkus.controller;

import com.social.quarkus.dto.PostResponse;
import com.social.quarkus.dto.ResponseError;
import com.social.quarkus.entities.Post;
import com.social.quarkus.entities.User;
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
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Path("/api/users/{user_id}/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {

    private PostRepository postRepository;
    private UserRepository userRepository;
    private Validator validator;
    @Inject
    public PostResource(PostRepository postRepository, UserRepository userRepository, Validator validator){
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.validator = validator;
    }


    @POST
    @Transactional
    public Response newPost(@PathParam("user_id") Long id, Post post){
        Set<ConstraintViolation<Post>> violations = validator.validate(post);
        if(!violations.isEmpty()){
            ResponseError responseError = ResponseError.createFromValidation(violations);
            return Response
                    .status(400)
                    .entity(responseError)
                    .build();
        }
        User user = userRepository.findById(id);
        Post newPost = new Post();
        newPost.setText(post.getText());
        newPost.setUser(user);

        postRepository.persist(newPost);

        return Response
                .status(Response.Status.CREATED.getStatusCode())
                .entity(user)
                .build();

    }
    @GET
    public Response listPosts(@PathParam("user_id") Long id){
       // PanacheQuery<User> user = userRepository.findById(id);
        User user = userRepository.findById(id);
        if(user != null){
            PanacheQuery<Post> query = postRepository.find("user", Sort.by("dateTime", Sort.Direction.Descending), user);
            List<Post> posts = query.list();

            List<PostResponse> postResponseList = posts.stream()
                    .map(post -> PostResponse.response(post))
                    .collect(Collectors.toList());
            return Response
                    .ok(postResponseList)
                    .build();

        }
        return Response.status(Response.Status.NOT_FOUND).build();

    }
    @DELETE
    public Response deletePost(Long id){
        Post post = postRepository.findById(id);
        if(post != null){
            postRepository.delete(post);
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();

    }

}
