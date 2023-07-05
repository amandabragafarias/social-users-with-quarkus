package com.social.quarkus.controller;

import com.social.quarkus.dto.ResponseError;
import com.social.quarkus.entities.User;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.json.Json;
import jakarta.json.bind.Jsonb;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class FollowerResourceTest {

}