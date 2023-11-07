@Regression
Feature: Create customer

  @Test
  Scenario: Validate customer creation with required fields
    Given Creates a customer with "required" fields
    And 201 response code should be received
#    Then a request sent to DXP will return user's minimaldetails

  Scenario: Validate customer creation with all fields
    Given Creates a customer with "all" fields
    And  201 response code should be received
#    Then a request sent to DXP will return user's full details

  Scenario: Validate customer creation with null values for mandatory attribute
    Given Create a customer with null values for mandatory attribute
    And  400 response code should be received

  Scenario: Validate customer creation with missing Brand
    Given Create a customer with empty brands attribute
    And  400 response code should be received

  Scenario: Validate customer Creation with missing HouseName and HouseNumber
    Given Create a customer with empty HouseName and HouseNumber attribute
    Then  500 response code should be received

  Scenario: Validate maximum length for FirstName and LastName fields
    Given Create a customer to validate Maximum Length For FirstName And LastName Fields
    And  500 response code should be received
    Then the response contains a message: "Attribute value longer than 50 symbols"

  Scenario: Validate If LegitimateInterest Is Defaulted To Proper Value
    Given Creates a customer with "required" fields
    And  201 response code should be received
    Then a request to DXP to retrieve information will include legitimate interest details
