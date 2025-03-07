Feature: OpenWeatherMap City Search

  @TC_001
  Scenario: Search for a city and verify display
    Given I open the OpenWeatherMap home page
    When I search for the city "Los Angeles, US"
    Then I should see the city name "Los Angeles, US" displayed correctly
