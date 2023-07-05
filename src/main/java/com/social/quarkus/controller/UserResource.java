package com.social.quarkus.controller;

import com.social.quarkus.dto.ResponseError;
import com.social.quarkus.entities.User;
import com.social.quarkus.repository.UserRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Set;

@Path("/api/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private UserRepository repository;
    private Validator validator;
    @Inject
    public UserResource (UserRepository repository, Validator validator){
        this.repository = repository;
        this.validator = validator;
    }

    @POST
    @Transactional
    public Response createUser(User user){
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if(!violations.isEmpty()){
            ResponseError responseError = ResponseError.createFromValidation(violations);
            return Response
                    .status(400)
                    .entity(responseError)
                    .build();
        }
        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setAge(user.getAge());
        repository.persist(newUser);
        return Response
                .status(Response.Status.CREATED.getStatusCode())
                .entity(user)
                .build();

    }
    @GET
    public Response findAllUsers(){
        PanacheQuery<User> users = repository.findAll();
        return Response.ok(users.list()).build();
    }
    @GET
    @Path("{id}")
    public Response findUserById(@PathParam("id") Long id){
       User user = repository.findById(id);
        if(user != null){
            return Response.ok(user).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteUser(@PathParam("id") Long id){
        User user = repository.findById(id);
        if(user != null){
            repository.delete(user);
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response updateUser(@PathParam("id") Long id, User userData){
        Set<ConstraintViolation<User>> violations = validator.validate(userData);
        if(!violations.isEmpty()){
            ResponseError responseError = ResponseError.createFromValidation(violations);
            return Response
                    .status(400)
                    .entity(responseError)
                    .build();
        }
        User user = repository.findById(id);
        if(user != null){
            user.setName(userData.getName());
            user.setAge(userData.getAge());
            user.setEmail(userData.getEmail());
            return Response
                    .noContent()
                    .build();
        }
        return Response
                .status(Response.Status.NOT_FOUND)
                .build();
    }
}
