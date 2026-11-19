package com.demoblaze.automationtest.runners;

import io.cucumber.junit.CucumberOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        plugin = {"pretty", "json:target/cucumber-reports/cucumber.json"},
        features = "src/test/resources/features/BEPMC",
        glue = "com.demoblaze.automationtest.glue",
        tags = "@e2e",
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class PurchaseE2ERunner {

    @BeforeClass
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }
}
