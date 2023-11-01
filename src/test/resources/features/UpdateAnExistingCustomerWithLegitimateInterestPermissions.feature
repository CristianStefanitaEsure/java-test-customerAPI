@Regression
Feature: Update an existing customer with Legitimate Interest permissions

  Scenario Outline: Modify LegitimateInterest MarketingPermissions
    Given Creates a customer with "required" fields
    And  201 response code should be received
    When there is a request to update customer with legitimate interest marketing permissions set to "<Operation>"
    Then LI response will include values set for the legitimate interest
    Examples:
      | Operation  |
      | TrueAll    |
      | FalseAll   |
      | EmailFalse |
      | SMSFalse   |
      | PostFalse  |
      | PhoneFalse |

  Scenario Outline: Modify The Customer's LegitimateInterest Permissions With Missing Channel,CanMarket and Source Value
    Given Creates a customer with "all" fields
    And  201 response code should be received
    When there is a request to update customer with legitimate interest marketing permissions set to "<Operation>"
    Then the Update LI request will be rejected with <Code> code
    Examples:
      | Operation     | Code |
      | ChannelNull   | 500  |
      | CanMarketNull | 500  |
      | SourceNull    | 500  |
      | ChannelEmpty  | 500  |
      | SourceEmpty   | 200  |

  Scenario: Verify default LegitimateInterest MarketingPermissions
    Given Creates a customer with "required" fields
    And  201 response code should be received
    Then LI response will include default values of legitimate interest


