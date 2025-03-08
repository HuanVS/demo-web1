package com.example.serenity.api.steps;

import io.restassured.response.Response;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.model.environment.SystemEnvironmentVariables;

import java.util.List;
import java.util.Map;
import java.util.Comparator;

public class GithubSteps {
    private Response response;
    private List<Map<String, Object>> repositories; // Lưu danh sách repo dạng Map

    @Step("Gọi API để lấy danh sách repositories của tổ chức {0}")
    public void getRepositories(String orgName) {
        // Lấy base url từ serenity.conf
//        String baseUrl = System.getProperty("api.base.url");
        SystemEnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
        String baseUrl = EnvironmentSpecificConfiguration.from(variables).getProperty("api.base.url");
        System.out.println("baseURL--"+baseUrl);

        response = SerenityRest.given()
                .baseUri(baseUrl)
                .basePath("/users/" + orgName + "/repos")
                .queryParam("per_page", 100) // Mặc định GitHub API trả 30, ta lấy 100
                .when()
                .get();

        // Lưu dữ liệu JSON trả về vào List<Map<String, Object>>
        repositories = response.jsonPath().getList("$");
    }

    @Step("Tính tổng số open issues cho tất cả repository")
    public int getTotalOpenIssues() {
        int totalOpenIssues = 0;
        for (Map<String, Object> repo : repositories) {
            // key "open_issues_count" của GitHub API
            // (xem https://docs.github.com/en/rest/repos/repos#list-repositories-for-a-user)
            Number issuesCount = (Number) repo.get("open_issues_count");
            if (issuesCount != null) {
                totalOpenIssues += issuesCount.intValue();
            }
        }
        return totalOpenIssues;
    }

    @Step("Sắp xếp repositories theo updated date giảm dần")
    public List<Map<String, Object>> sortReposByUpdatedDateDesc() {
        repositories.sort((r1, r2) -> {
            String date1 = (String) r1.get("updated_at");
            String date2 = (String) r2.get("updated_at");
            // So sánh date theo chuỗi ISO-8601, hoặc convert sang Instant
            // Cách đơn giản:
            return date2.compareTo(date1);
        });
        return repositories;
    }

    @Step("Lấy repository có số watchers nhiều nhất")
    public Map<String, Object> getRepoWithMostWatchers() {
        // GitHub API: key "watchers_count"
        return repositories.stream()
                .max(Comparator.comparing(r -> ((Number)r.get("watchers_count")).intValue()))
                .orElse(null);
    }

    @Step("Xác nhận status code là {0}")
    public void verifyStatusCode(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @Step("In ra thông tin test (không bắt buộc)")
    public void printResult() {
        // In ra console hoặc log
        System.out.println("Số lượng repository: " + repositories.size());
    }
}
