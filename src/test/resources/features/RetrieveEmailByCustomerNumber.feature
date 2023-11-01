@Regression
Feature: Retrieve customer email by customer number

  Scenario: Retrieve customer email for customer with email address
    Given Creates a customer with "all" fields
    And  201 response code should be received
    When a user sends GET request to retrieve email by "valid" customerID
    Then the get Email by customerID response will include email data sent in request

  Scenario: Retrieve customer email for customer with no email address
    Given Creates a customer with "required" fields
    And  201 response code should be received
    When a user sends GET request to retrieve email by "valid" customerID
    Then the get Email by customerID response will return empty email

  Scenario: Retrieve customer email for non-existing customer
    Given there is a request to retrieve customer email for "nonExisting" customer
    Then  500 response code should be received by getEmail by customerId api

  Scenario: Retrieve customer email for customer with wildcard
    Given there is a request to retrieve customer email for "wildcard" customer
    And  500 response code should be received by getEmail by customerId api