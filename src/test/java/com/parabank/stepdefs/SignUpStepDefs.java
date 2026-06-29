package com.parabank.stepdefs;

import com.parabank.context.TestContext;
import com.parabank.pages.HomePage;
import com.parabank.pages.RegisterPage;
import com.parabank.utils.TestDataGenerator;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

/**
 * Step definitions for all Registration (Sign-Up) scenarios.
 *
 * TestContext is injected by Cucumber PicoContainer — the same instance
 * is shared with SignInStepDefs, allowing registered credentials to flow
 * between sign-up and sign-in steps within the same scenario.
 */
public class SignUpStepDefs {

    private final TestContext context;

    public SignUpStepDefs(TestContext context) {
        this.context = context;
    }

    // ──────────────────────────────────────────────────────────
    // GIVEN
    // ──────────────────────────────────────────────────────────

    @Given("I am on the ParaBank home page")
    public void iAmOnTheParaBankHomePage() {
        HomePage homePage = new HomePage(context.getPage());
        homePage.navigate();
        Assertions.assertTrue(homePage.isLoaded(),
                "ParaBank home page should be loaded successfully");
        System.out.println("[INFO] Navigated to ParaBank home page.");
    }

    // ──────────────────────────────────────────────────────────
    // WHEN
    // ──────────────────────────────────────────────────────────

    @When("I click the Register link")
    public void iClickTheRegisterLink() {
        HomePage homePage = new HomePage(context.getPage());
        homePage.clickRegister();
        System.out.println("[INFO] Clicked Register link.");
    }

    @When("I register a new account with valid details")
    public void iRegisterANewAccountWithValidDetails() {
        RegisterPage registerPage = new RegisterPage(context.getPage());

        String username = TestDataGenerator.generateUniqueUsername();
        String password = TestDataGenerator.getDefaultPassword();

        System.out.println("[INFO] Registering with username: " + username);

        registerPage.fillPersonalDetails(
                TestDataGenerator.getFirstName(),
                TestDataGenerator.getLastName(),
                TestDataGenerator.getAddress(),
                TestDataGenerator.getCity(),
                TestDataGenerator.getState(),
                TestDataGenerator.getZipCode(),
                TestDataGenerator.getPhone(),
                TestDataGenerator.getSsn()
        );
        registerPage.fillCredentials(username, password);

        // Store credentials in shared context so sign-in steps can use them
        context.setRegisteredUsername(username);
        context.setRegisteredPassword(password);

        registerPage.submit();
        System.out.println("[INFO] Registration form submitted.");
    }

    @When("I submit the registration form without filling any fields")
    public void iSubmitRegistrationFormWithoutFillingAnyFields() {
        RegisterPage registerPage = new RegisterPage(context.getPage());
        registerPage.submit();
        System.out.println("[INFO] Submitted empty registration form.");
    }

    // ──────────────────────────────────────────────────────────
    // THEN
    // ──────────────────────────────────────────────────────────

    @Then("I should be on the registration page")
    public void iShouldBeOnTheRegistrationPage() {
        RegisterPage registerPage = new RegisterPage(context.getPage());
        Assertions.assertTrue(registerPage.isOnRegisterPage(),
                "User should be redirected to the registration page");
        System.out.println("[INFO] Confirmed: on registration page.");
    }

    @Then("I should see a successful registration message")
    public void iShouldSeeASuccessfulRegistrationMessage() {
        RegisterPage registerPage = new RegisterPage(context.getPage());
        Assertions.assertTrue(registerPage.isRegistrationSuccessful(),
                "Expected a successful registration confirmation message");
        System.out.println("[INFO] Registration confirmed. Message: "
                + registerPage.getWelcomeMessage());
    }

    @Then("I should see validation error messages")
    public void iShouldSeeValidationErrorMessages() {
        RegisterPage registerPage = new RegisterPage(context.getPage());
        Assertions.assertTrue(registerPage.hasValidationErrors(),
                "Expected validation error messages for empty form submission");
        System.out.println("[INFO] Validation errors displayed: "
                + registerPage.getValidationErrorCount() + " error(s).");
    }
}
