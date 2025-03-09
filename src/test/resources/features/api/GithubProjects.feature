@API
Feature: Github API Testing

  @API_TC001
  Scenario: Get repositories and verify required info
    Given User want to retrieve repositories for "SeleniumHQ"
    Then User should see the total open issues across all repositories
    And  User should see the repositories sorted by updated date descending
    And  User should see which repository has the most watchers

