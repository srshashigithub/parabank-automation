# ============================================================
# Feature: User Registration (Sign Up)
# Covers: successful registration, form navigation, validation
# ============================================================

@registration
Feature: User Registration on ParaBank
  As a new visitor to ParaBank
  I want to register for a new account
  So that I can access banking services

  Background:
    Given I am on the ParaBank home page

  # TC-REG-001: Navigate to registration page
  @smoke @TC-REG-001
  Scenario: Navigate to the registration page from home
    When I click the Register link
    Then I should be on the registration page

  # TC-REG-002: Successful new account registration
  @smoke @TC-REG-002
  Scenario: Successfully register a new user account
    When I click the Register link
    Then I should be on the registration page
    When I register a new account with valid details
    Then I should see a successful registration message

  # TC-REG-003: Registration form validation - empty submission
  @negative @TC-REG-003
  Scenario: Submitting an empty registration form shows validation errors
    When I click the Register link
    Then I should be on the registration page
    When I submit the registration form without filling any fields
    Then I should see validation error messages
