package com.example.serenity.runner;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.example.serenity.defs"},
        tags = "@API_TC001",
        plugin = {"pretty", "html:target/cucumber-report.html"}
)
public class CucumberTestSuite {
}
