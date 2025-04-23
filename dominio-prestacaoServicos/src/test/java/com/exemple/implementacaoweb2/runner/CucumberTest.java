package com.exemple.implementacaoweb2.runner;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.exemple.implementacaoweb2.steps",
        plugin = {"pretty", "summary"},
        monochrome = true
)


public class CucumberTest {
}
