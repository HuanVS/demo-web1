package com.example.serenity.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OpenWeatherMapHomePage extends BasePage {

    @FindBy(xpath = "//input[@placeholder='Search city']")
    private WebElement searchInput;

    @FindBy(xpath = "//button[contains(text(),'Search')]")
    private WebElement searchButton;

    @FindBy(xpath = "//div[contains(@class,'current-container')]//h2")
    private WebElement cityNameLabel;

    @FindBy(xpath = "//div[contains(@class,'current-container')]/div/span")
    WebElement currentDate;

    @FindBy(xpath = "//div[contains(@class,'current-temp')]/span")
    WebElement temperature;

    // open OpenWeatherMap
    public void openHomePage() {
        open();
        getDriver().manage().window().maximize();
    }

    // search City
    public void searchCity(String city) {
        try {
            waitForVisibility(searchInput, 10);
            enterText(searchInput, city);
            clickElement(searchButton);
            waitABit(2000);
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
            waitABit(1000);
            return getTextElement(cityNameLabel).trim();
        } catch (Exception e) {
            System.out.println("Error in getDisplayedCityName: " + e.getMessage());
            return "";
        }
    }

    public String getCurrentDate() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d");
        return today.format(formatter);
    }

    public boolean isDateDisplayedCorrectly() {
        return currentDate.getText().contains(getCurrentDate());
    }

    public boolean isTemperatureDisplayedCorrectly() {
        return temperature.getText().matches("-?\\d+°[CF]");
    }
}
