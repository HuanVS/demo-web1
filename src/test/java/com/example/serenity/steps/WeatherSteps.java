package com.example.serenity.steps;

import com.example.serenity.pages.OpenWeatherMapHomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import net.serenitybdd.annotations.Step;
import org.junit.Assert;

public class WeatherSteps {

    OpenWeatherMapHomePage openWeatherMapHomePage;

    @Step
    public void openWeb(){
        openWeatherMapHomePage.openHomePage();
    }

    @Step
    public void searchCity(String cityName){
        openWeatherMapHomePage.searchCity(cityName);
    }

    @Step
    public void verifyCityNameInResult(String expectedCity){
        String actualCity = openWeatherMapHomePage.getDisplayedCityName();
        Assert.assertTrue("City name did not match! Expected to contain: " + expectedCity,
                actualCity.contains(expectedCity));
    }

    @Step
    public void verifyCurrentDate(){
        Assert.assertTrue(openWeatherMapHomePage.isDateDisplayedCorrectly());
    }

    @Step
    public void verifyTemperature(){
        Assert.assertTrue(openWeatherMapHomePage.isTemperatureDisplayedCorrectly());
    }
}
