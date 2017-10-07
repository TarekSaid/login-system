@ignored
Feature: Profile Operations
  As a user
  In order to review my information
  I want to see my profile

  Scenario:
    Given that I am logged in with "user" and password "password"
    When I access my profile
    Then I should see my name as "Onibus Espacial LTDA" and business as "Loja Virtual" and website as "www.onibusespacial.com.br"