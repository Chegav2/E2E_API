package com.demoblaze.automationtest.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfirmationAmount implements Question<Integer> {

    public static ConfirmationAmount value() {
        return new ConfirmationAmount();
    }

    @Override
    public Integer answeredBy(Actor actor) {
        // Texto típico del modal:
        // "Id: 1234\nAmount: 790 USD\nCard Number: **** **** **** 4242\n..."
        String details = new ConfirmationDetails().answeredBy(actor);
        if (details == null) {
            return null;
        }

        Pattern pattern = Pattern.compile("Amount:\\s*(\\d+)\\s*USD", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(details);
        if (matcher.find()) {
            try {
                return Integer.parseInt(matcher.group(1));
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }
}
