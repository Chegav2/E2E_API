package com.demoblaze.automationtest.tasks;

import net.serenitybdd.core.steps.UIInteractionSteps;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;

/** Abre la app por URL directa (robusto a cambios de anotaciones). */
public class OpenTheApplication extends UIInteractionSteps {

    private static final String BASE_URL = System.getProperty("baseUrl", "https://www.demoblaze.com/");

    public static Performable onHomePage() {
        return Task.where("{0} abre la tienda Demoblaze",
                Open.url(BASE_URL)
        );
    }
}
