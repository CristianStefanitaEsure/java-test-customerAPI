@Regression @PutUpdate
Feature: Update an existing customer with the given details

  Scenario: Modify Customer's Data
    Given Creates a customer with "All" fields
    And  201 response code should be received
    When a user sends PUT request to modify the customer with "FullDetails"
    Then 200 response code should be received from the Update Customer details response
    And the customer data will be modified

  Scenario: Update Customer - Add Bars
    Given Creates a customer with "All" fields
    And  201 response code should be received
    When a user sends PUT request to modify the customer with "Bars"
    Then the customer "modified" bars data will match requested data
    When a user sends PUT request to modify the customer with "customerBars" as "missing"
    Then 200 response code should be received from the Update Customer details response
    And the body response for updated request has "not-empty" value for "customerBars"
    And the get by customerID response will return "not-empty" value for "customerBars"

  Scenario: Update Customer - Add second Bars
    Given Creates a customer with "All" fields
    And  201 response code should be received
    And a user sends PUT request to modify the customer with "Bars"
    And the customer "modified" bars data will match requested data
    When there is request to "add" bar to a customer with "UW_INV"
    Then the customer "full" bars data will match requested data

  Scenario: Update Customer - Remove Bars
    Given Creates a customer with "All" fields
    And  201 response code should be received
    When there is request to "add" bar to a customer with "UWBAR"
    And there is a request to remove Bars by setting end date to 2 years earlier
    Then the customer "removed" bars data will match requested data
    And a request sent to DXP will return user's minimaldetails

  Scenario: Update Customer with Incorrect Data
    Given Creates a customer with "required" fields
    And  201 response code should be received
    When a user sends PUT request to modify the customer with "IncorrectFirstName"
    Then 500 response code should be received from the Update Customer details response


  # BPCI-3249 -> AC 2 & 4
  Scenario Outline: Update Customer by adding "<TYPE_FIELD>" with "<VALUE>"
    Given Creates a customer with "required" fields
    And  201 response code should be received
    When a user sends PUT request to modify the customer with "<TYPE_FIELD>" as "<VALUE>"
    Then 200 response code should be received from the Update Customer details response
    And the body response for updated request has "<LIST_TYPE>" value for "<TYPE_FIELD>"
    And the get by customerID response will return "<LIST_TYPE>" value for "<TYPE_FIELD>"
    Examples:
      | TYPE_FIELD          | VALUE | LIST_TYPE |
      | customerEmails      | valid | not-empty |
      | customerPhones      | valid | not-empty |
      | customerEmployments | valid | not-empty |
      | customerEmails      | empty | empty     |
      | customerEmails      | null  | empty     |
      | customerPhones      | empty | empty     |
      | customerEmployments | empty | empty     |


  Scenario Outline: Update Customer with invalid Email
    Given Creates a customer with "all" fields
    And  201 response code should be received
    When a user sends PUT request to modify the customer with "customerEmails" as "<EMAIL_VALUE>"
    Then <RESPONSE_CODE> response code should be received from the Update Customer details response
    And the update customer's email response contains a message: "<MESSAGE>"
    Examples:
      | EMAIL_VALUE | RESPONSE_CODE | MESSAGE                       |
      | testsss     | 500           | Failed to update EIS Customer |
      | test@c      | 500           | Failed to update EIS Customer |
      | test.com    | 500           | Failed to update EIS Customer |

 # BPCI-3249 -> AC 5
  Scenario Outline: Update Customer with "<TYPE_FIELD>" missing - positive
    Given Creates a customer with "all" fields
    And  201 response code should be received
    When a user sends PUT request to modify the customer with "<TYPE_FIELD>" as "<VALUE>"
    Then 200 response code should be received from the Update Customer details response
    And the body response for updated request has "not-empty" value for "<TYPE_FIELD>"
    And the get by customerID response will return "not-empty" value for "<TYPE_FIELD>"
    Examples:
      | TYPE_FIELD                                     | VALUE   |
      | customerEmails                                 | missing |
      | customerPhones                                 | missing |
      | customerEmployments                            | missing |
      | customerLegitimateInterestMarketingPermissions | missing |

     # BPCI-3249 -> AC 6
  Scenario Outline: Update Customer with "<TYPE_FIELD>" missing - negative
    Given Creates a customer with "all" fields
    And  201 response code should be received
    When a user sends PUT request to modify the customer with "<TYPE_FIELD>" as "<VALUE>"
    Then <RESPONSE_CODE> response code should be received from the Update Customer details response
    And the update customer's email response contains a message: "<MESSAGE>"
    Examples:
      | TYPE_FIELD               | VALUE   | RESPONSE_CODE | MESSAGE     |
      | brands                   | missing | 400           | Bad Request |
      | customerAddress          | missing | 400           | Bad Request |
      | customerIndividualDetail | missing | 400           | Bad Request |


  Scenario Outline: Update Customer by adding "<TYPE_FIELD>" with multiple values
    Given Creates a customer with "required" fields
    And  201 response code should be received
    When a user sends PUT request to modify the customer with "<TYPE_FIELD>" as "<VALUE>"
    Then 200 response code should be received from the Update Customer details response
    When a user sends PUT request to modify the customer with "<TYPE_FIELD>" as "<MULTIPLE_VALUE>"
    Then 200 response code should be received from the Update Customer details response
    Examples:
      | TYPE_FIELD          | VALUE | MULTIPLE_VALUE |
      | customerPhones      | valid | multipleValues |
      | customerEmployments | valid | multipleValues |
