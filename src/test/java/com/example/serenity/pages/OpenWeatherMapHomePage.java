package com.example.serenity.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class OpenWeatherMapHomePage extends BasePage {

    @FindBy(xpath = "//input[@placeholder='Search city']")
    private WebElement searchInput;

    @FindBy(xpath = "//button[contains(text(),'Search')]")
    private WebElement searchButton;

    @FindBy(xpath = "//div[contains(@class,'current-container')]//h2")
    private WebElement cityNameLabel;

    // open OpenWeatherMap
    public void openHomePage() {
        openUrl("https://openweathermap.org/");
        getDriver().manage().window().maximize();
    }

    // search City
    public void searchCity(String city) {
        try {
            waitForVisibility(searchInput, 10);
            enterText(searchInput, city);
            clickElement(searchButton);
            String dynamicXpath = String.format("//span[contains(text(),'%s')]/..", city);
            getDriver().findElement(By.xpath(dynamicXpath)).click();
        } catch (Exception e) {
            System.out.println("Error in searchCity: " + e.getMessage());
            throw e;
        }
    }

    // get city name
    public String getDisplayedCityName() {
        try {
            return getTextElement(cityNameLabel);
        } catch (Exception e) {
            System.out.println("Error in getDisplayedCityName: " + e.getMessage());
            return "";
        }
    }
}
