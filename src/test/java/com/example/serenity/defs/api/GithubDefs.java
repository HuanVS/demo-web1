package com.example.serenity.defs.api;

import com.example.serenity.steps.api.GithubSteps;
import io.cucumber.java.en.Given;
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

    @Given("User want to retrieve repositories for {string}")
    public void iWantToRetrieveRepositoriesForOrg(String orgName) {
        githubSteps.getRepositories(orgName);
        githubSteps.verifyStatusCode(200);
        githubSteps.printResult();
    }

    @Then("User should see the total open issues across all repositories")
    public void iShouldSeeTheTotalOpenIssues() {
        totalOpenIssues = githubSteps.getTotalOpenIssues();
        System.out.println("Total open issues: " + totalOpenIssues);
    }

    @Then("User should see the repositories sorted by updated date descending")
    public void iShouldSeeTheRepositoriesSortedByUpdatedDateDescending() {
        sortedRepos = githubSteps.sortReposByUpdatedDateDesc();
        // Check first element must have latest updated_at compared to next element
        for (int i = 0; i < sortedRepos.size() - 1; i++) {
            String current = (String) sortedRepos.get(i).get("updated_at");
            String next = (String) sortedRepos.get(i+1).get("updated_at");
            Assert.assertTrue(current.compareTo(next) >= 0);
        }
        System.out.println("Repositories are sorted descending by updated_at");
    }

    @Then("User should see which repository has the most watchers")
    public void iShouldSeeWhichRepositoryHasTheMostWatchers() {
        mostWatchedRepo = githubSteps.getRepoWithMostWatchers();
        if (mostWatchedRepo != null) {
            System.out.println("repository with most watchers: "
                    + mostWatchedRepo.get("name")
                    + " - watchers_count=" + mostWatchedRepo.get("watchers_count"));
        }
    }
}
