# Leasing Demo Project - Products API test

Serenity BDD is a library that makes it easier to write high quality automated acceptance tests, with powerful reporting and living documentation features. It has strong support API testing using RestAssured.

![example workflow](https://github.com/perovicp/leaseplan-example/actions/workflows/maven.yml/badge.svg)
## The leasing demo project
This project has been done as SerenityRest demo for Project application

## Cucumber Run configuration for Cucumber Java
```sh
Mainclass: net.serenitybdd.cucumber.cli.Main
Glue: net.serenitybdd.cucumber.actors starter.stepdefinitions
```

### The project directory structure

src
  + main
  + test
    + java                        Test runners and configuration code
    + resources
      + features                    Feature files
     + search                  Feature file subdirectories 
             products.feature
```

```Gherkin
Feature: Search by keyword

  Scenario: Searching for a term
    Given Sergey is researching things on the internet
    When he looks up "Cucumber"
    Then he should see information about "Cucumber"
```

### Refactored
Refactored was almost everything, Products class, stepdefinitions, feature files

### The Action Classes implementation.

A more imperative-style implementation using the Action Classes pattern can be found in the `action-classes` branch. The glue code in this version looks this this:

```java
    @When("GET request ist sent to {string}")
    public void getRequestIsSent(String endpoint) {
        SerenityRest.given().get(HOME + endpoint);
    }
```

## Executing the tests

By default, maven clean verify will execute all the API Integration tests
```json
$ mvn clean verify
```
The test results will be recorded in the `target/site/serenity/index.html`.

## Generating the reports
Since the Serenity reports contain aggregate information about all of the tests, they are not generated after each individual test (as this would be extremenly inefficient). Rather, The Full Serenity reports are generated by the `serenity-maven-plugin`. You can trigger this by running `mvn serenity:aggregate` from the command line or from your IDE.

They reports are also integrated into the Maven build process: the following code in the `pom.xml` file causes the reports to be generated automatically once all the tests have completed when you run `mvn verify`?

```
             <plugin>
                <groupId>net.serenity-bdd.maven.plugins</groupId>
                <artifactId>serenity-maven-plugin</artifactId>
                <version>${serenity.maven.version}</version>
                <configuration>
                    <tags>${tags}</tags>
                </configuration>
                <executions>
                    <execution>
                        <id>serenity-reports</id>
                        <phase>post-integration-test</phase>
                        <goals>
                            <goal>aggregate</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
```

You use the `environment` system property to determine which environment to run against. For example to run the tests in the staging environment, you could run:
```json
$ mvn clean verify -Denvironment=staging
```