package com.demoblaze.automationtest.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;

public class HomeTargets {
    public static final Target PRODUCT_LINK = Target.the("link del producto {0}")
            .locatedBy("//a[contains(@class,'hrefch') and normalize-space()='{0}']");
}
