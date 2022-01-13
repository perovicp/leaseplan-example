#language:en
Feature: Positive tests for Apple products (the ones you eat)
  @Smoke
Scenario Outline: Positive - Apples response exists
When GET request ist sent to <apple_endpoint>
Then results are returned successful
  And results are in JSON format
  Examples:
  |apple_endpoint|
  |"apple"|

    @Integration
Scenario Outline: Positive - Apple product response has at least one Apple Product
When GET request ist sent to <apple_endpoint>
Then results have at Least One Element of <product> Product type
  Examples:
    |apple_endpoint| product|
    |"apple"| Apple|
  @Inte
  Scenario Outline: Negative - Apple product response doesn't have Mango Product
    When GET request ist sent to <apple_endpoint>
    Then results haven't Element of <product> Product type
    Examples:
      |apple_endpoint| product|
      |"apple"| Mango|

  Scenario Outline: Positive - Mango product response has at least one Apple Product
    When GET request ist sent to <mango_endpoint>
    Then results have at Least One Element of <product> Product type
    Examples:
      |mango_endpoint| product|
      |"mango"| Mango|

  Scenario Outline: Negative - Mango product response doesn't have Apple Product
    When GET request ist sent to <mango_endpoint>
    Then results haven't Element of <product> Product type
    Examples:
      |mango_endpoint| product|
      |"mango"| Apple |

  Scenario Outline: Negative - Asking for a Cars on Car Endpoint response should fail
    When GET request ist sent to <apple_endpoint>
    Then results are returned as not successful
    And results are in JSON format
    Examples:
      |apple_endpoint|
      |"car"|