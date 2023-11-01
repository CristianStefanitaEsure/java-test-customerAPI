@Regression
Feature: EndToEnd customer test

  @BPCI-4515
  Scenario: Validate customer creation with the required fields
    Given Creates a customer with "all" fields
    And 201 response code should be received
    And a request sent to DXP will return user's full details
    And there is a request to update customer email address with a "newone"
    And the email address will be updated
    And the get Email by customerID response will include email data sent in request
    And there is a request to update customer with legitimate interest marketing permissions set to "EmailFalse"
    And LI response will include values set for the legitimate interest
    And there is request to "add" bar to a customer with "UWBAR"
    And the customer "full" bars data will match requested data
    When a user sends GET request to retrieve customer details by "Updated" email
    Then a request to get customer by email to java api returns a empty customer
