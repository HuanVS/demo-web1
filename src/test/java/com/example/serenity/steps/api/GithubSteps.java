package com.example.serenity.steps.api;

import io.restassured.response.Response;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;

import java.util.List;
import java.util.Map;
import java.util.Comparator;

public class GithubSteps {
    private Response response;
    private EnvironmentVariables environmentVariables;
    private List<Map<String, Object>> repositories; // Stored repo as Map

    @Step("call API get list repositories of {0}")
    public void getRepositories(String orgName) {
        // Lấy base url từ serenity.conf
        String baseUrl = EnvironmentSpecificConfiguration.from(environmentVariables).getProperty("rest.base.url");
        response = SerenityRest.given()
                .baseUri(baseUrl)
                .basePath("/users/" + orgName + "/repos")
                .queryParam("per_page", 100) // default GitHub API return 30, change to get 100
                .when()
                .get();

        // Stored JSON to List<Map<String, Object>>
        repositories = response.jsonPath().getList("$");
    }

    @Step("Count sum of open issues for all repository")
    public int getTotalOpenIssues() {
        int totalOpenIssues = 0;
        for (Map<String, Object> repo : repositories) {
            // key "open_issues_count" of GitHub API
            // (refer https://docs.github.com/en/rest/repos/repos#list-repositories-for-a-user)
            Number issuesCount = (Number) repo.get("open_issues_count");
            if (issuesCount != null) {
                totalOpenIssues += issuesCount.intValue();
            }
        }
        return totalOpenIssues;
    }

    @Step("Sort repositories by update date descending")
    public List<Map<String, Object>> sortReposByUpdatedDateDesc() {
        repositories.sort((r1, r2) -> {
            String date1 = (String) r1.get("updated_at");
            String date2 = (String) r2.get("updated_at");
            // Compare date by ISO-8601, or convert to Instant
            // simple way:
            return date2.compareTo(date1);
        });
        return repositories;
    }

    @Step("Get repository with most watchers")
    public Map<String, Object> getRepoWithMostWatchers() {
        // GitHub API: key "watchers_count"
        return repositories.stream()
                .max(Comparator.comparing(r -> ((Number)r.get("watchers_count")).intValue()))
                .orElse(null);
    }

    @Step("Verify status code is {0}")
    public void verifyStatusCode(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @Step("Print out test information")
    public void printResult() {
        // Or Logging
        System.out.println("Number of repository: " + repositories.size());
    }
}
