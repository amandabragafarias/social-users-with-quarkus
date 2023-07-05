package com.social.quarkus.controller;

import com.social.quarkus.dto.ResponseError;
import com.social.quarkus.entities.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

class UserResourceTest {
    @Test
    @DisplayName("Should create a user sucessfully")
    public void createUserTest(){
        User user = new User();
        user.setName("TestingName");
        user.setAge(25);
        user.setEmail("testing@email.com");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/users")
                .then()
                .extract()
                .response();
        assertEquals(jakarta.ws.rs.core.Response.Status.CREATED, response.statusCode());
        assertNotNull(response.jsonPath().getString("id"));
    }

    @Test
    @DisplayName("Error for invalid json")
    public void createUserValidationTest(){
        User user = new User();
        user.setName(null);
        user.setAge(null);
        user.setEmail(null);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/users")
                .then()
                .extract()
                .response();
        assertEquals(400, response.statusCode());
        assertEquals("Validation Error", response.jsonPath().getString("message"));

    }


}