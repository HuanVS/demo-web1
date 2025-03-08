@API
Feature: Github API Testing

  @API_TC001
  Scenario: Get repositories and verify required info
    Given I want to retrieve repositories for "SeleniumHQ"
    When I fetch the list of repositories
    Then I should see the total open issues across all repositories
    And I should see the repositories sorted by updated date descending
    And I should see which repository has the most watchers

