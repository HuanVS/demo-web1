package com.example.serenity.pages;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage extends PageObject {

    public void waitForVisibility(WebElement element, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            System.out.println("Error waiting for visibility: " + e.getMessage());
            throw e;
        }
    }


    /**
     wait for element clickable
     */
    public void waitForClickable(WebElement element, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            System.out.println("Error waiting for clickable: " + e.getMessage());
            throw e;
        }
    }
    /**
     input text
     */

    public void enterText(WebElement element, String text) {
        try {
            waitForVisibility(element, 10);
            element.clear();
            element.sendKeys(text);
        } catch (Exception e) {
            System.out.println("Error entering text: " + e.getMessage());
            throw e;
        }
    }

    public void clickElement(WebElement element) {
        try {
            element.click();
        } catch (Exception e) {
            System.out.println("Error clicking element: " + e.getMessage());
            throw e;
        }
    }

    public String getTextElement(WebElement element) {
        try {
            return element.getText();
        } catch (Exception e) {
            System.out.println("Error getting text from element: " + e.getMessage());
            return "";
        }
    }
    /**
     scroll using JS
     */

    public void scrollToElement(WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) getDriver();
            js.executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (Exception e) {
            System.out.println("Error scrolling to element: " + e.getMessage());
        }
    }
}
