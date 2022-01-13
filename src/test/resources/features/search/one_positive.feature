#language:en
Feature: Positive tests for Apple products (the ones you eat)

Scenario Outline: Positive - Apples response exists
When GET request ist sent to <apple_endpoint>
Then results are returned successful
  And results are in JSON format
  Examples:
  |apple_endpoint|
  |"apple"|

Scenario Outline: Positive - Apple product response has at least one Apple Product
When GET request ist sent to <apple_endpoint>
Then results have at Least One Element of <product> Product type
  Examples:
    |apple_endpoint| product|
    |"apple"| Apple|

  Scenario Outline: Negative - Apple product response doesn't have Mango Product
    When GET request ist sent to <apple_endpoint>
    Then results haven't Element of <product> Product type
    Examples:
      |apple_endpoint| product|
      |"apple"| Mango|
