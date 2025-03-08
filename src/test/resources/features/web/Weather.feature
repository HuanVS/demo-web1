@Web
Feature: OpenWeatherMap City Search

  @Web_TC001
  Scenario Outline: Search for a city and verify display
    Given User open the OpenWeatherMap home page
    When User search for the city "<cityName>"
    Then User should see the city name "<cityName>" displayed correctly
    And  User should see the current date is displayed correctly
    And  User should see the temperature displayed as a number

    Examples:
      | cityName        |
      | Los Angeles, US |
