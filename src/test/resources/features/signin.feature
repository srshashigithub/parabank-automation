# ============================================================
# Feature: User Sign In
# Covers: valid login with account balance, invalid login
# ============================================================

@signin
Feature: User Sign In on ParaBank
  As a registered user
  I want to sign in to my ParaBank account
  So that I can view my account balance and manage my finances

  # TC-LOGIN-001: Successful login and account balance display
  # Registers a fresh account first, then signs in with those credentials
  @smoke @TC-LOGIN-001
  Scenario: Sign in with valid registered credentials and view account balance
    Given I am on the ParaBank home page
    When I click the Register link
    Then I should be on the registration page
    When I register a new account with valid details
    Then I should see a successful registration message
    When I sign in with the registered credentials
    Then I should be on the Accounts Overview page
    And the account balance should be printed to console

  # TC-LOGIN-002: Invalid credentials show error message
  @negative @TC-LOGIN-002
  Scenario: Attempting to sign in with invalid credentials shows an error
    Given I am on the ParaBank home page
    When I sign in with invalid credentials
    Then I should see an error message
