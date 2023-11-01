@Regression
Feature: Find or Create Customer

  @Test
  Scenario: Ensure customer is not created on findOrCreate endpoint because it's already exist
    Given Creates a customer with "all" fields
    And  201 response code should be received
    And "full" Customer response should be matched
    And a request sent to DXP will return user's minimaldetails
    When same customer request sent to FindorCreate endpoint
    And  200 response code should be received
    Then customer Id should match
    And "latestPCWQuoteDate" will be "LocalDate" in the dxp response

  Scenario: Create a customer without isPCWQuoteRequest as parameter using findOrCreate API
    Given Create a customer "withoutflag" using FindorCreate endpoint
    And  200 response code should be received
    And "full" Customer response should be matched
    When a request sent to DXP will return user's minimaldetails
    Then "latestPCWQuoteDate" will be "null" in the dxp response

  Scenario: Create a customer with isPCWQuoteRequest=true as parameter using findOrCreate API
    Given Create a customer "withflag" using FindorCreate endpoint
    And  200 response code should be received
    And "full" Customer response should be matched
    When a request sent to DXP will return user's minimaldetails
    Then "latestPCWQuoteDate" will be "LocalDate" in the dxp response

  Scenario: validate if null Firtname will get rejected for the same request to findOrCreate API
    Given Creates a customer with "all" fields
    And  201 response code should be received
    And "full" Customer response should be matched
    When a request sent to DXP will return user's minimaldetails
    And same customer request sent to FindorCreate endpoint updating "FirstName" to "null"
    Then  400 response code should be received

  Scenario: Provided Customer does not exists and the email is Unique
    Given Creates a customer with "all" fields
    And  201 response code should be received
    When Customer Creation Response should contain isEmailUnique flag "true"
    Then Email is the same as provided in the request

  Scenario: Provided Customer does not exists and the email is not Unique
    Given Creates a customer with "all" fields
    And  201 response code should be received
    When same customer request sent to FindorCreate endpoint updating "Email" to "NewCustomerWithExistingEmail"
    Then Customer Creation Response should contain isEmailUnique flag "false"
    And Email is Empty for isEmailUnique testcase

  Scenario: Validate findOrCreate request with existing customer with same customer's email
    Given Creates a customer with "all" fields
    And  201 response code should be received
    And "full" Customer response should be matched
    When a request sent to DXP will return user's minimaldetails
    And same customer request sent to FindorCreate endpoint
    Then  200 response code should be received
    And customer Email should match

  Scenario: Validate findOrCreate request with existing customer with different existing customer's email
    Given Creates a customer with "all" fields
    And  201 response code should be received
    And "full" Customer response should be matched
    And a request sent to DXP will return user's minimaldetails
    And Creates a customer with "all" fields
    And  201 response code should be received
    And "full" Customer response should be matched
    When same customer request sent to FindorCreate endpoint updating "Email" to "FirstCustomersEmail"
    And  200 response code should be received
    Then customer Email should not match
    And Email uniqueness should be "false"

  Scenario: Validate findOrCreate request with existing customer with different unique email
    Given Creates a customer with "all" fields
    And  201 response code should be received
    And "full" Customer response should be matched
    When a request sent to DXP will return user's minimaldetails
    And same customer request sent to FindorCreate endpoint updating "Email" to "new"
    Then  200 response code should be received
    And Email uniqueness should be "true"

  Scenario: Provided Customer does exists and the email is already assigned to the same customer
    Given Creates a customer with "all" fields
    And  201 response code should be received
    When same customer request sent to FindorCreate endpoint updating "Email" to "sameEmail"
    Then Email is the same as provided in the request
    And Email uniqueness should be "true"
