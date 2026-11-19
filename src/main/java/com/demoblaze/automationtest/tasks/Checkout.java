package com.demoblaze.automationtest.tasks;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;
import com.demoblaze.automationtest.interactions.RememberCartTotal;
import com.demoblaze.automationtest.userinterfaces.CartPage;
import com.demoblaze.automationtest.userinterfaces.CheckoutModal;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class Checkout {

    public static Performable withData(String name, String country, String city, String card, String month, String year) {
        return Task.where("{0} completa el formulario de compra y finaliza",
                // Recordar total del carrito antes de abrir el modal
                RememberCartTotal.now(),

                WaitUntil.the(CartPage.PLACE_ORDER_BUTTON, isClickable()).forNoMoreThan(20).seconds(),
                Click.on(CartPage.PLACE_ORDER_BUTTON),

                WaitUntil.the(CheckoutModal.NAME, isVisible()).forNoMoreThan(15).seconds(),
                Enter.theValue(name).into(CheckoutModal.NAME),
                Enter.theValue(country).into(CheckoutModal.COUNTRY),
                Enter.theValue(city).into(CheckoutModal.CITY),
                Enter.theValue(card).into(CheckoutModal.CARD),
                Enter.theValue(month).into(CheckoutModal.MONTH),
                Enter.theValue(year).into(CheckoutModal.YEAR),

                Click.on(CheckoutModal.PURCHASE_BUTTON)
        );
    }
}
