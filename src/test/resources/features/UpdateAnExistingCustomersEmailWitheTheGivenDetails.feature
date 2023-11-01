@Regression
Feature: Search for a customerNumber by exact match

  Scenario: Update the existing Customer's Email with new one
    Given Creates a customer with "all" fields
    And  201 response code should be received
    When there is a request to update customer email address with a "newone"
    Then the email address will be updated
    And the get Email by customerID response will include email data sent in request

  Scenario Outline: Update the existing Customer's Email with invalid emailID
    Given Creates a customer with "all" fields
    And  201 response code should be received
    When there is a request to update customer email ID with a "<EMAIL_ID>"
    Then  the request will be rejected with <RESPONSE_CODE> code
    And the update customer's email response contains a message: "<MESSAGE>"
    Examples:
      | EMAIL_ID | RESPONSE_CODE | MESSAGE                              |
      | INVALID  | 500           | The provided email id does not exist |
      | EMPTY    | 405           | Method Not Allowed                   |

  Scenario Outline: Update the existing Customer's Email with Invalid, Empty and Null email address
    Given Creates a customer with "all" fields
    And  201 response code should be received
    When there is a request to update customer email address with a "<EMAIL_ADDRESS>"
    Then   the request will be rejected with <RESPONSE_CODE> code
    Examples:
      | EMAIL_ADDRESS | RESPONSE_CODE |
      | INVALID       | 500           |
      | EMPTY         | 400           |
      | NULL          | 400           |

  Scenario: Update the existing Customer's Email With null EmailConfirmed Field
    Given Creates a customer with "all" fields
    And  201 response code should be received
    When there is a request to update customer email with EmailConfirmed as "null"
    Then   the request will be rejected with 400 code
