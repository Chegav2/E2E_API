package com.demoblaze.automationtest.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;

public class CartPage {
    public static final Target CART_LINK = Target.the("link Cart")
            .locatedBy("//*[@id='cartur' or normalize-space()='Cart']");

    public static final Target PLACE_ORDER_BUTTON = Target.the("botón Place Order")
            .locatedBy("//button[normalize-space()='Place Order']");

    public static final Target CART_PRICE_CELLS = Target.the("celdas de precio en el carrito")
            .locatedBy("//*[@id='tbodyid']/tr/td[3]");

    public static final Target TOTAL_PRICE = Target.the("total del carrito (#totalp)")
            .locatedBy("//*[@id='totalp']");
}
