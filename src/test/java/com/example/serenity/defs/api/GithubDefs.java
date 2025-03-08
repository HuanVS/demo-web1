package com.example.serenity.defs.api;

import com.example.serenity.api.steps.GithubSteps;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import net.serenitybdd.annotations.Steps;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class GithubDefs {

    @Steps
    GithubSteps githubSteps;

    private List<Map<String, Object>> sortedRepos;
    private Map<String, Object> mostWatchedRepo;
    private int totalOpenIssues;

    @Given("I want to retrieve repositories for {string}")
    public void iWantToRetrieveRepositoriesForOrg(String orgName) {
        githubSteps.getRepositories(orgName);
        githubSteps.verifyStatusCode(200);
        githubSteps.printResult();
    }

    @When("I fetch the list of repositories")
    public void iFetchTheListOfRepositories() {
        // Ở đây ta có thể làm thêm step nếu cần
    }

    @Then("I should see the total open issues across all repositories")
    public void iShouldSeeTheTotalOpenIssues() {
        totalOpenIssues = githubSteps.getTotalOpenIssues();
        System.out.println("Total open issues: " + totalOpenIssues);
    }

    @Then("I should see the repositories sorted by updated date descending")
    public void iShouldSeeTheRepositoriesSortedByUpdatedDateDescending() {
        sortedRepos = githubSteps.sortReposByUpdatedDateDesc();
        // Kiểm tra phần tử đầu tiên phải có updated_at mới nhất so với phần tử tiếp theo
        for (int i = 0; i < sortedRepos.size() - 1; i++) {
            String current = (String) sortedRepos.get(i).get("updated_at");
            String next = (String) sortedRepos.get(i+1).get("updated_at");
            Assert.assertTrue(current.compareTo(next) >= 0);
        }
        System.out.println("Repositories đã được sort giảm dần theo updated_at");
    }

    @Then("I should see which repository has the most watchers")
    public void iShouldSeeWhichRepositoryHasTheMostWatchers() {
        mostWatchedRepo = githubSteps.getRepoWithMostWatchers();
        if (mostWatchedRepo != null) {
            System.out.println("Repository có số watchers nhiều nhất: "
                    + mostWatchedRepo.get("name")
                    + " - watchers_count=" + mostWatchedRepo.get("watchers_count"));
        }
    }
}
