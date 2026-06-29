# ============================================================
# Feature: End-to-End Account Registration and Login Flow
# This is the PRIMARY test covering the core requirement:
#   1. Create a new ParaBank account
#   2. Sign in with that account
#   3. Print the account balance shown post-login
# ============================================================

@e2e
Feature: End-to-End Account Creation and Sign In Flow
  As a new user of ParaBank
  I want to create an account and sign in with it
  So that I can access and view my account balance

  # TC-E2E-001: Complete user journey - Register then Sign In
  @smoke @TC-E2E-001
  Scenario: Register a new account and sign in to view account balance
    Given I am on the ParaBank home page
    When I click the Register link
    Then I should be on the registration page
    When I register a new account with valid details
    Then I should see a successful registration message
    When I sign in with the registered credentials
    Then I should be on the Accounts Overview page
    And the account balance should be printed to console
