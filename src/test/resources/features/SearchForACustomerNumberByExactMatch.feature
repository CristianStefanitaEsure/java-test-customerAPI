@Regression
Feature: Search for a customer number by exact match

  Scenario: Customer Search Post Request with all the fields
    Given Creates a customer with "all" fields
    And  201 response code should be received
    When a user sends a POST request to retrieve customerID "withHousenumber" using search API
    Then a user will receive customer id in the search customer response body

  Scenario: Customer Search with HouseName And HouseNumber
    Given Creates a customer with "all" fields
    And  201 response code should be received
    When a user sends a POST request to retrieve customerID "withHousenumberAndHouseName" using search API
    Then a user will receive customer id in the search customer response body

  Scenario: Customer Search Without HouseName
    Given Creates a customer with "NoHousename" fields
    And  201 response code should be received
    When a user sends a POST request to retrieve customerID "withoutHouseName" using search API
    Then a user will receive customer id in the search customer response body

  Scenario: Customer Search Without HouseNumber
    Given Creates a customer with "NoHousenumber" fields
    And  201 response code should be received
    When a user sends a POST request to retrieve customerID "withoutHouseNumber" using search API
    Then a user will receive customer id in the search customer response body

  Scenario: Customer Search with incorrect HouseName
    Given Creates a customer with "all" fields
    And  201 response code should be received
    When a user sends a POST request to retrieve customerID "IncorrectHouseName" using search API
    Then the search customer response is empty

  Scenario: Customer Search with incorrect HouseNumber
    Given Creates a customer with "all" fields
    And  201 response code should be received
    When a user sends a POST request to retrieve customerID "IncorrectHouseNumber" using search API
    Then the search customer response is empty

  Scenario: Search customer with incorrect details
    Given Creates a customer with "all" fields
    And  201 response code should be received
    When a user sends a POST request to retrieve customerID "WithIncorrectDetails" using search API
    Then the search customer response is empty