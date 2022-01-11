#language:en
Feature: Positive tests for Apple products (the ones you eat)

Scenario Outline: Positive - Apples response exists
When GET request ist sent to <apple_endpoint>
Then results are returned successful
  And results are in JSON format
  Examples:
  |apple_endpoint|
  |"apple"|

#Scenario Outline: Positive - Apples response exists
#When GET request ist sent to <apple_endpoint>
#Then results are displayed for <product>
#  Examples:
#    |apple_endpoint| product|
#    |"apple"| "apple"|
