package com.parabank.stepdefs;

import com.parabank.context.TestContext;
import com.parabank.pages.AccountOverviewPage;
import com.parabank.pages.HomePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

/**
 * Step definitions for all Sign-In and Account Overview scenarios.
 *
 * Relies on TestContext (injected by PicoContainer) to receive the
 * username/password that was generated and stored by SignUpStepDefs
 * during the same scenario.
 */
public class SignInStepDefs {

    private final TestContext context;

    public SignInStepDefs(TestContext context) {
        this.context = context;
    }

    // ──────────────────────────────────────────────────────────
    // WHEN
    // ──────────────────────────────────────────────────────────

    /**
     * Signs in using credentials stored in TestContext after registration.
     * Performs a logout first to ensure we start from the login form,
     * since registration automatically logs the user in on ParaBank.
     */
    @When("I sign in with the registered credentials")
    public void iSignInWithTheRegisteredCredentials() {
        String username = context.getRegisteredUsername();
        String password = context.getRegisteredPassword();

        Assertions.assertNotNull(username, "Registered username must be available in context");
        Assertions.assertNotNull(password, "Registered password must be available in context");

        System.out.println("[INFO] Logging out to reach login form...");
        HomePage homePage = new HomePage(context.getPage());
        homePage.logout();

        System.out.println("[INFO] Signing in with username: " + username);
        homePage.login(username, password);
    }

    @When("I sign in with invalid credentials")
    public void iSignInWithInvalidCredentials() {
        System.out.println("[INFO] Attempting sign-in with invalid credentials...");
        HomePage homePage = new HomePage(context.getPage());
        homePage.login("invalid_user_xyz_999", "WrongPassword!99");
    }

    // ──────────────────────────────────────────────────────────
    // THEN / AND
    // ──────────────────────────────────────────────────────────

    @Then("I should be on the Accounts Overview page")
    public void iShouldBeOnTheAccountsOverviewPage() {
        AccountOverviewPage overviewPage = new AccountOverviewPage(context.getPage());
        overviewPage.waitUntilLoaded();
        boolean displayed = overviewPage.isDisplayed();
        System.out.println("[INFO] Accounts Overview page displayed: " + displayed);
        // ParaBank shows the overview after successful login; URL contains "overview" or site root
        Assertions.assertTrue(displayed,
                "User should be on the Accounts Overview page after login. Current URL: "
                        + context.getPage().url());
        System.out.println("[INFO] Confirmed: on Accounts Overview page.");
    }

    @And("the account balance should be printed to console")
    public void theAccountBalanceShouldBePrintedToConsole() {
        AccountOverviewPage overviewPage = new AccountOverviewPage(context.getPage());
        overviewPage.printAccountBalances();
        String total = overviewPage.getTotalBalance();
        System.out.println("[INFO] Total balance retrieved: " + total);
        Assertions.assertNotNull(total, "Total balance should not be null");
    }

    @Then("I should see an error message")
    public void iShouldSeeAnErrorMessage() {
        HomePage homePage = new HomePage(context.getPage());
        Assertions.assertTrue(homePage.isLoginErrorDisplayed(),
                "An error message should be displayed for invalid credentials");
        System.out.println("[INFO] Login error message: " + homePage.getLoginErrorText());
    }
}
