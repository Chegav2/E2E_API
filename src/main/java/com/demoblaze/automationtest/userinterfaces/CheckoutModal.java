package com.demoblaze.automationtest.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;

public class CheckoutModal {
    public static final Target NAME    = Target.the("Nombre").locatedBy("//input[@id='name']");
    public static final Target COUNTRY = Target.the("País").locatedBy("//input[@id='country']");
    public static final Target CITY    = Target.the("Ciudad").locatedBy("//input[@id='city']");
    public static final Target CARD    = Target.the("Tarjeta").locatedBy("//input[@id='card']");
    public static final Target MONTH   = Target.the("Mes").locatedBy("//input[@id='month']");
    public static final Target YEAR    = Target.the("Año").locatedBy("//input[@id='year']");
    public static final Target PURCHASE_BUTTON = Target.the("botón Purchase")
            .locatedBy("//button[normalize-space()='Purchase']");
}
