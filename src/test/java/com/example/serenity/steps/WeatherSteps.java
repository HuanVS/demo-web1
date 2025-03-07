package com.example.serenity.steps;

import com.example.serenity.pages.OpenWeatherMapHomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;

public class WeatherSteps {

    OpenWeatherMapHomePage openWeatherMapHomePage;

    @Given("I open the OpenWeatherMap home page")
    public void i_open_the_open_weather_map_home_page() {
        openWeatherMapHomePage.openHomePage();
    }

    @When("I search for the city {string}")
    public void i_search_for_the_city(String city) {
        openWeatherMapHomePage.searchCity(city);
    }

    @Then("I should see the city name {string} displayed correctly")
    public void i_should_see_the_city_name_displayed_correctly(String expectedCity) {
        String actualCity = openWeatherMapHomePage.getDisplayedCityName();
        Assert.assertTrue("City name did not match! Expected to contain: " + expectedCity,
                actualCity.contains(expectedCity));
    }
}
