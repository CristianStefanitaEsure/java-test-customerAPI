@Regression
Feature: Update an existing customers with Bars

  Scenario: Add UW Bar To A Customer
    Given Creates a customer with "required" fields
    And  201 response code should be received
    When there is request to "add" bar to a customer with "UWBAR"
    Then the customer "full" bars data will match requested data

  Scenario: Add Mandatory Only Fields Bar To A Customer
    Given Creates a customer with "required" fields
    And  201 response code should be received
    When there is request to "add" bar to a customer with "MandatoryFieldsOnly"
    Then the customer "minimal" bars data will match requested data

  Scenario: Add Second Bar To A Customer
    Given Creates a customer with "required" fields
    And  201 response code should be received
    And there is request to "add" bar to a customer with "UWBAR"
    And the customer "minimal" bars data will match requested data
    When there is request to "add" bar to a customer with "SECONDBARS"
    Then the customer second bars data will match requested data

  Scenario: Modify Customer Bar
    Given Creates a customer with "required" fields
    And  201 response code should be received
    And there is request to "add" bar to a customer with "UWBAR"
    And the customer "full" bars data will match requested data
    When there is request to "UpdateExisting" bar to a customer with "reason=DISCR"
    Then the customer "full" bars data will match requested data

  Scenario: Delete Customer Bar
    Given Creates a customer with "required" fields
    And  201 response code should be received
    When there is request to "add" bar to a customer with "UWBAR"
    And there is a request to remove Bars by setting end date to 2 years earlier
    Then the customer "removed" bars data will match requested data

  Scenario Outline: Add Account-Bar,Account-Flag,PolicyValidationBar,FruadBar and Underwriting - Investigation Bars To A Customer
    Given Creates a customer with "required" fields
    And  201 response code should be received
    When there is request to "add" bar to a customer with "<BAR>"
    Then the customer "full" bars data will match requested data
    Examples:
      | BAR     |
      | ACC_BAR |
      | ACC_FLG |
      | VAL_INV |
      | FRD_BAR |
      | UW_INV  |

  Scenario Outline: Validate Bar's Attributes for "<BAR>" with message "<MESSAGE>"
    Given Creates a customer with "required" fields
    And  201 response code should be received
    And customer is available for customer search
    When there is request to "add" bar to a customer with "<BAR>"
    Then <RESPONSE_CODE> response code should be received by putBar api
    And putBar response should contain massage : "<MESSAGE>"
    Examples:
      | BAR                                     | RESPONSE_CODE | MESSAGE                                           |
      | NullBarType                             | 500           | Bar Type must be supplied                         |
      | IncorrectBarType                        | 500           | BarType lookup code must be one of these values   |
      | UWBarWithoutCategory                    | 500           | Category must be supplied                         |
      | UWBarWitIncorrectCategory               | 500           | Failed to update customer                         |
      | UWBarWithoutReason                      | 500           | Reason must be supplied                           |
      | UWBarWitIncorrectReason                 | 500           | BarReason lookup code must be one of these values |
      | PolicyValidationBarWithoutCategory      | 500           | Category must be supplied                         |
      | PolicyValidationBarWitIncorrectCategory | 500           | Failed to update customer                         |
      | PolicyValidationBarWithoutReason        | 500           | Reason must be supplied                           |
      | PolicyValidationBarWitIncorrectReason   | 500           | BarReason lookup code must be one of these values |
      | FraudBarWithoutCategory                 | 500           | Category must be supplied                         |
      | FraudBarWitIncorrectCategory            | 500           | Failed to update customer                         |
      | FraudBarWithoutReason                   | 500           | Reason must be supplied                           |
      | FraudBarWitIncorrectReason              | 500           | BarReason lookup code must be one of these values |
      | AccountFlagWithCategory                 | 500           | Failed to update customer                         |
      | AccountFlagWithReason                   | 500           | Failed to update customer                         |
      | AccountBarWithCategory                  | 500           | Failed to update customer                         |
      | AccountBarWithReason                    | 500           | Failed to update customer                         |

