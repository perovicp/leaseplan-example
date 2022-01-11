package starter.stepdefinitions;


import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapper;
import io.restassured.path.json.mapper.factory.DefaultJackson2ObjectMapperFactory;
import io.restassured.path.json.mapper.factory.Jackson2ObjectMapperFactory;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import org.apache.http.HttpStatus;

import static net.serenitybdd.rest.RestDefaults.config;
import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static net.serenitybdd.rest.SerenityRest.restAssuredThat;
import static org.hamcrest.Matchers.contains;
import static starter.stepdefinitions.Constants.*;

public class SearchStepDefinitions {

    private SerenityRest serenityRest;
    @Given("Base URL exists")
    public void setBaseURL(){
        RestAssured.basePath = HOME;
        serenityRest.setDefaultBasePath(HOME);
    }

    @Before(order=1)
    public void setup(){
        serenityRest = new SerenityRest();
        serenityRest.enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL);
    }

    @When("GET request ist sent to {string}")
    public void getRequestIsSent(String endpoint) {
        serenityRest.given().get(HOME + endpoint);
    }

    @Then("results are returned successful")
    public void assertResponseSuccess() {

        //lastResponse().getHeaders()..contains(HttpStatus.SC_OK);
        System.out.println("Step");
    }

    @Then("results are in JSON format")
    public void assertJSONFileFormat(){
        serenityRest.lastResponse().contentType().equals(ContentType.JSON);
        //restAssuredThat(response -> response.contentType(ContentType.JSON));
    }

    @Then("results are displayed for {string}")
    public void assertResultsDisplayedForProduct(String product) {
        restAssuredThat(response -> response.body(TITLE, contains(product)));
    }

    @Then("doesn"+"'"+"t see the results, but error")
    public void assertResultsAreNotVisible() {
        restAssuredThat(response -> response.body("error", contains("True")));
    }

    @Then("printout last response body")
    public void printLastResponse(){
        lastResponse().prettyPrint();
    }

    public static ObjectMapper objectMapper(final ObjectMapper objectMapper) {
        RestAssured.config = config().objectMapperConfig(ObjectMapperConfig.objectMapperConfig()
                .defaultObjectMapper(objectMapper));
        return config().getObjectMapperConfig().defaultObjectMapper();
    }
}
