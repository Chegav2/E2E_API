package com.demoblaze.automationtest.interactions;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class RememberCartTotal implements Interaction {

    public static RememberCartTotal now() {
        return Tasks.instrumented(RememberCartTotal.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Demoblaze calcula y muestra el total en #totalp de forma dinámica.
        // Esperamos ese elemento y lo usamos como "fuente de la verdad".
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("totalp")));
            String totalText = driver.findElement(By.id("totalp")).getText();
            String digits = totalText.replaceAll("[^0-9]", "");
            if (!digits.isEmpty()) {
                Serenity.setSessionVariable("expectedTotal").to(Integer.parseInt(digits));
                return;
            }
        } catch (Exception ignored) {
        }

        // Fallback: sumar celdas de precio por fila si #totalp no está listo
        List<WebElement> prices = driver.findElements(By.xpath("//*[@id='tbodyid']/tr/td[3]"));
        int sum = 0;
        for (WebElement el : prices) {
            try {
                String txt = el.getText().replaceAll("[^0-9]", "");
                if (!txt.isEmpty())
                    sum += Integer.parseInt(txt);
            } catch (Exception ignored) {
            }
        }
        Serenity.setSessionVariable("expectedTotal").to(sum);
    }
}
