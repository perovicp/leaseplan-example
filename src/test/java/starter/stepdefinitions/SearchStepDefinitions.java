package starter.stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.ResponseBody;
import net.serenitybdd.rest.SerenityRest;
import org.apache.http.HttpStatus;
import org.assertj.core.api.ArraySortedAssert;
import org.slf4j.Logger;
import starter.AppleProduct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.serenitybdd.rest.RestDefaults.config;
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

    @Then("results has at Least One Element of AppleProduct type")
    public void assertResultsDisplayedForProduct() {
        ResponseBody<?> rb = SerenityRest.lastResponse().getBody();
        //info
        assertThat(rb.jsonPath().getList(".",AppleProduct.class)).hasAtLeastOneElementOfType(AppleProduct.class);
        List<AppleProduct> appleProducts = rb.jsonPath().getList(".",AppleProduct.class);
        System.out.println("Product list number: " + appleProducts.stream().count());
    }

    @Then("doesn"+"'"+"t see the results")
    public void assertResultsAreNotVisible() {
        restAssuredThat(response -> response.body("error", contains("True")));
    }

    @Then("printout last response body")
    public void printLastResponse(){
        lastResponse().prettyPrint();
    }
}
