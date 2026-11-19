package com.demoblaze.automationtest.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import com.demoblaze.automationtest.userinterfaces.CartPage;

public class OpenCart {
    public static Performable view() {
        return Task.where("{0} abre el carrito",
                Click.on(CartPage.CART_LINK)
        );
    }
}
