package com.demoblaze.automationtest.tasks;

import net.serenitybdd.core.steps.UIInteractionSteps;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.waits.WaitUntil;
import com.demoblaze.automationtest.interactions.AcceptAlert;
import com.demoblaze.automationtest.userinterfaces.HomeTargets;
import com.demoblaze.automationtest.userinterfaces.ProductPage;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;

public class AddProduct extends UIInteractionSteps {

    private static final String BASE_URL = System.getProperty("baseUrl", "https://www.demoblaze.com/");

    public static Performable named(String productName) {
        return Task.where("{0} agrega el producto " + productName,
                WaitUntil.the(HomeTargets.PRODUCT_LINK.of(productName), isClickable()).forNoMoreThan(20).seconds(),
                Click.on(HomeTargets.PRODUCT_LINK.of(productName)),
                WaitUntil.the(ProductPage.ADD_TO_CART, isClickable()).forNoMoreThan(20).seconds(),
                Click.on(ProductPage.ADD_TO_CART),
                AcceptAlert.now(),
                // Navegación robusta al inicio (evita dependencias textuales)
                Open.url(BASE_URL)
        );
    }
}
