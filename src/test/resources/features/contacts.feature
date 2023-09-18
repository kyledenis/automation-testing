@web
Feature: Contact

  @Contact
  Scenario: Verify display of Error Message for Invalid Email id and Telephone on Contact Page
    Given A Customer tries to contact us
    When Customer enters invalid Email id and invalid Telephone
    Then Error message for invalid email and invalid telephone are displayed

  @Contact
  Scenario: Verify display of mandatory field error messages on Contact Page
    Given A Customer tries to contact us
    When Customer submits empty information
    Then Errors for mandatory information are displayed

  # @Contact
  # Scenario: Ensure Acknowledgement message is displayed when customer submits mandatory information on Contact Page
  #   Given A Customer tries to contact us
  #   When Customer submits all mandatory information
  #     | ForeName    | Email                | Telephone   | Message              |
  #     | TestUserone | Testuserone@test.com | 0412411011 | Testuser message one |
  #   Then Acknowledgement message is displayed
