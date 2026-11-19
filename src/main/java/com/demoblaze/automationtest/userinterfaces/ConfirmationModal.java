package com.demoblaze.automationtest.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;

public class ConfirmationModal {
    public static final Target CONFIRMATION_TITLE = Target.the("título de confirmación")
            .locatedBy("//h2[contains(normalize-space(),'Thank you for your purchase!')]");

    public static final Target CONFIRMATION_DETAILS = Target.the("detalles de confirmación")
            .locatedBy("//div[contains(@class,'sweet-alert') and contains(@class,'visible')]//p");
}
