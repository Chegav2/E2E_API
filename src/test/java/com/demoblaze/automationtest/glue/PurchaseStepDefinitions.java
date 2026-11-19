package com.demoblaze.automationtest.glue;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.GivenWhenThen;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.questions.Visibility;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import com.demoblaze.automationtest.questions.ConfirmationAmount;
import com.demoblaze.automationtest.questions.ConfirmationDetails;
import com.demoblaze.automationtest.tasks.*;
import com.demoblaze.automationtest.util.PurchaseDataReader;

import java.util.List;
import java.util.Map;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static org.hamcrest.Matchers.equalTo;
import static com.demoblaze.automationtest.userinterfaces.ConfirmationModal.CONFIRMATION_TITLE;

public class PurchaseStepDefinitions {

    private Actor cheddy = Actor.named("Cheddy Gavilanes");

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
        WebDriver driver = new ChromeDriver();
        cheddy.can(BrowseTheWeb.with(driver));
    }

    @Given("que abro la tienda")
    public void queAbroLaTienda() {
        cheddy.attemptsTo(OpenTheApplication.onHomePage());
    }

    @When("agrego los siguientes productos al carrito:")
    public void agregoLosSiguientesProductosAlCarrito(DataTable table) {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            String producto = row.getOrDefault("producto", "").trim();
            cheddy.attemptsTo(AddProduct.named(producto));
        }
    }

    @When("agrego los productos del caso:")
    public void agregoLosProductosDelCaso(DataTable table) {
        Map<String, String> data = PurchaseDataReader.load(table.cell(1, 0));
        cheddy.attemptsTo(
                AddProduct.named(data.get("product1")),
                AddProduct.named(data.get("product2"))
        );
    }

    @When("voy al carrito")
    public void voyAlCarrito() {
        cheddy.attemptsTo(OpenCart.view());
    }

    @When("realizo la compra con los datos:")
    public void realizoLaCompraConLosDatos(DataTable table) {
        Map<String, String> data = table.asMap(String.class, String.class);
        cheddy.attemptsTo(Checkout.withData(
                data.getOrDefault("nombre", "Tester"),
                data.getOrDefault("pais", "Uruguay"),
                data.getOrDefault("ciudad", "Montevideo"),
                data.getOrDefault("tarjeta", "4242424242424242"),
                data.getOrDefault("mes", "12"),
                data.getOrDefault("anio", "2025")
        ));
    }

    @When("realizo la compra con los datos del caso:")
    public void realizoLaCompraConLosDatosDelCaso(DataTable table) {
        Map<String, String> data = PurchaseDataReader.load(table.cell(1, 0));
        cheddy.attemptsTo(Checkout.withData(
                data.get("name"),
                data.get("country"),
                data.get("city"),
                data.get("card"),
                data.get("month"),
                data.get("year")
        ));
    }

    @Then("veo la confirmación de compra")
    public void veoLaConfirmacionDeCompra() {
        cheddy.attemptsTo(
                WaitUntil.the(CONFIRMATION_TITLE, isVisible()).forNoMoreThan(20).seconds()
        );
        cheddy.should(GivenWhenThen.seeThat(
                "El título de confirmación es visible",
                Visibility.of(CONFIRMATION_TITLE),
                equalTo(true)
        ));

        String details = cheddy.asksFor(ConfirmationDetails.text());
        Serenity.recordReportData().withTitle("Detalles de la compra").andContents(details);
    }

    @Then("el total de compra coincide con la suma del carrito")
    public void elTotalDeCompraCoincideConLaSumaDelCarrito() {
        Integer expected = Serenity.sessionVariableCalled("expectedTotal");
        Integer actual = cheddy.asksFor(ConfirmationAmount.value());
        Serenity.recordReportData()
                .withTitle("Comparación de totales")
                .andContents("Esperado (suma carrito): " + expected + " | Confirmación: " + actual);
        Assert.assertEquals("El total de confirmación debe coincidir con la suma del carrito", expected, actual);
    }
}
