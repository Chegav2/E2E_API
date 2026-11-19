package com.demoblaze.automationtest.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import com.demoblaze.automationtest.userinterfaces.ConfirmationModal;

public class ConfirmationDetails implements Question<String> {

    public static ConfirmationDetails text() {
        return new ConfirmationDetails();
    }

    @Override
    public String answeredBy(Actor actor) {
        return Text.of(ConfirmationModal.CONFIRMATION_DETAILS).answeredBy(actor);
    }
}
