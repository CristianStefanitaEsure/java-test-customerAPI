@Regression
Feature: Retrieve customer by email

  @Test @BPCI-4515
  Scenario: Search for customer using exact same email address
    Given Creates a customer with "all" fields
    And  201 response code should be received
    When a user sends GET request to retrieve customer details by "valid" email
    Then a request to get customer by email to java api returns a empty customer

  @BPCI-4515
  Scenario: Verify if search functionality is case sensitive
    Given Creates a customer with "all" fields
    And  201 response code should be received
    When a user sends GET request to retrieve customer details by "uppercase" email
    Then a request to get customer by email to java api returns a empty customer

  Scenario: Search for customer using non-existing email address
    Given Creates a customer with "all" fields
    And  201 response code should be received
    When a user sends GET request to retrieve customer details by "nonExisting" email
    Then a request to get customer by email to java api returns a empty customer

  Scenario: Search for customer using  EmailAddress with Wildcard
    Given Creates a customer with "all" fields
    And  201 response code should be received
    When a user sends GET request to retrieve customer details by "EmailAddresswithWildcard" email
    Then a request to get customer by email to java api returns a empty customer

  Scenario: Search for customer only with Wildcard
    Given Creates a customer with "all" fields
    And  201 response code should be received
    When a user sends GET request to retrieve customer details by "onlywildcard" email
    Then a request to get customer by email to java api returns a empty customer
