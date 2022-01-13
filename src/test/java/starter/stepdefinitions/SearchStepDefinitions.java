package starter.stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.ResponseBody;
import net.serenitybdd.rest.SerenityRest;
import org.apache.http.HttpStatus;
import starter.stepdefinitions.dto.Product;

import java.util.List;

import static net.serenitybdd.rest.SerenityRest.*;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.hamcrest.Matchers.contains;
import static starter.stepdefinitions.Constants.*;

public class SearchStepDefinitions {

    @Given("Base URL exists")
    public void setBaseURL(){
        RestAssured.basePath = HOME;
        SerenityRest.setDefaultBasePath(HOME);
        RestAssured.config = getDefaultConfig();
    }

    @Before(order=1)
    public void setup(){
        SerenityRest.enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL);
    }

    @When("GET request ist sent to {string}")
    public void getRequestIsSent(String endpoint) {
        SerenityRest.given().get(HOME + endpoint);
    }

    @Then("results are returned successful")
    public void assertResponseSuccess() {
        assertThat(lastResponse().getStatusCode()).isEqualTo( HttpStatus.SC_OK);
    }

    @Then("results are in JSON format")
    public void assertJSONFileFormat(){
        assertThat(SerenityRest.lastResponse().contentType()).isEqualTo(ContentType.JSON.toString());
    }

    @Then("results have at Least One Element of {word} Product type")
    public void assertResultsDisplayedForProduct(String product) {
        ResponseBody<?> rb = SerenityRest.lastResponse().getBody();
        //Any of elements are  product type
        assertThat(rb.jsonPath().getList(".", Product.class)).hasAtLeastOneElementOfType(Product.class);
        List<Product> products = rb.jsonPath().getList(".", Product.class);
        // Any of Elements hast in the title product word
        assertThat(products.stream().anyMatch(p -> p.getTitle().contains(product.toLowerCase()))).isTrue();
    }

    @Then("results haven't Element of {word} Product type")
    public void assertResultsDoesntHaveProduct(String product) {
        ResponseBody<?> rb = SerenityRest.lastResponse().getBody();
        //Any of elements are Apple product type
        assertThat(rb.jsonPath().getList(".", Product.class)).hasAtLeastOneElementOfType(Product.class);
        List<Product> products = rb.jsonPath().getList(".", Product.class);
        // Any of Elements hast in the title product word
        assertThat(products.stream().anyMatch(p -> p.getTitle().contains(product.toLowerCase()))).isFalse();
    }

    @Then("doesn"+"'"+"t see the results")
    public void assertResultsAreNotVisible() {
        restAssuredThat(response -> response.body("error", contains("True")));
    }
}
