Feature: User Operations
  As a user
  In order to use the system
  I want to get my login information

  Scenario: find logged user information
    Given that I am logged in with "user" and password "password"
    When I fetch my information
    Then I should see the username "user"
