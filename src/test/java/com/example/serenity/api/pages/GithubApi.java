package com.example.serenity.api.pages;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;
import io.restassured.response.Response;

public class GithubApi {

    private static final String BASE_URL = "https://api.github.com";

    @Step("Lấy danh sách repositories của tổ chức {0}")
    public Response getRepositories(String organization) {
        return SerenityRest.given()
                .baseUri(BASE_URL)
                .when()
                .get("/orgs/" + organization + "/repos")
                .andReturn();
    }
}
