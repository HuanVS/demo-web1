package com.example.serenity.defs.web;

import com.example.serenity.steps.web.WeatherSteps;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;

public class WeatherDefs {
    @Steps
    WeatherSteps weatherSteps;

    @Given("User open the OpenWeatherMap home page")
    public void open_the_open_weather_map_home_page() {
        weatherSteps.openWeb();
    }

    @When("User search for the city {string}")
    public void search_for_the_city(String city) {
        weatherSteps.searchCity(city);
    }

    @Then("User should see the city name {string} displayed correctly")
    public void should_see_the_city_name_displayed_correctly(String expectedCity) {
        weatherSteps.verifyCityNameInResult(expectedCity);
    }

    @And("User should see the current date is displayed correctly")
    public void userShouldSeeTheCurrentDateIsDisplayedCorrectly() {
        weatherSteps.verifyCurrentDate();
    }

    @And("User should see the temperature displayed as a number")
    public void userShouldSeeTheTemperatureDisplayedAsANumber() {
        weatherSteps.verifyTemperature();
    }
}
