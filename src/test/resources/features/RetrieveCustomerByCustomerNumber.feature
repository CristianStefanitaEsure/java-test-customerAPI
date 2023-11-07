@Regression
Feature: Retrieve customer by customerNumber

  Scenario: Validate customer creation with required fields
    Given Creates a customer with "required" fields
    And  201 response code should be received
#    Then a request sent to DXP will return user's minimaldetails

  Scenario: Get customer by customer number with proper customer id using customer java api
    Given Creates a customer with "required" fields
    And  201 response code should be received
    When a user sends GET request to retrieve customer details by customer number
    Then a request to get customer details to java api returns a valid response

  Scenario: Verify emails and phones confirmations for a new customer
    Given Creates a customer with "required" fields
    And  201 response code should be received
    When a user sends GET request to retrieve customer details by customer number
    Then a request to get customer details to java api returns a valid Emails And Phones

  Scenario: Verify LegitimateInterest is present for a new customer
    Given Creates a customer with "required" fields
    And  201 response code should be received
    When a user sends GET request to retrieve customer details by customer number
    Then a request to get customer details to java api returns a valid LI data

  Scenario: Verify get customer with non existing CustomerId
    Given a user sends GET request to retrieve customer details with "invalid" ID
    And  404 response code should be received by getcustomer api
    Then a getcustomer response contains a message: "Failed to retrieve customer"

  Scenario: Verify get customer with CustomerId as Wildmark
    Given a user sends GET request to retrieve customer details with "*" ID
    And  404 response code should be received by getcustomer api
    Then a getcustomer response contains a message: "Failed to retrieve customer"