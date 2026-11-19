package com.demoblaze.automationtest.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;

public class ProductPage {
    public static final Target ADD_TO_CART = Target.the("botón Agregar al carrito")
            .locatedBy("//a[normalize-space()='Add to cart']");

    public static final Target NAV_HOME = Target.the("link brand PRODUCT STORE")
            .locatedBy("//*[@id='nava']");
}
