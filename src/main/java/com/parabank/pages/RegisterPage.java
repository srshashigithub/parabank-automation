package com.parabank.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * Page Object for ParaBank Registration Page.
 * URL: /parabank/register.htm
 */
public class RegisterPage {

    private final Page page;

    // --- Locators (using name attributes to avoid CSS dot-escaping issues) ---
    private final Locator firstNameInput;
    private final Locator lastNameInput;
    private final Locator addressInput;
    private final Locator cityInput;
    private final Locator stateInput;
    private final Locator zipCodeInput;
    private final Locator phoneInput;
    private final Locator ssnInput;
    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator confirmPasswordInput;
    private final Locator registerButton;

    public RegisterPage(Page page) {
        this.page = page;
        this.firstNameInput      = page.locator("input[name='customer.firstName']");
        this.lastNameInput       = page.locator("input[name='customer.lastName']");
        this.addressInput        = page.locator("input[name='customer.address.street']");
        this.cityInput           = page.locator("input[name='customer.address.city']");
        this.stateInput          = page.locator("input[name='customer.address.state']");
        this.zipCodeInput        = page.locator("input[name='customer.address.zipCode']");
        this.phoneInput          = page.locator("input[name='customer.phoneNumber']");
        this.ssnInput            = page.locator("input[name='customer.ssn']");
        this.usernameInput       = page.locator("input[name='customer.username']");
        this.passwordInput       = page.locator("input[name='customer.password']");
        this.confirmPasswordInput = page.locator("input[id='repeatedPassword']");
        this.registerButton      = page.locator("input[value='Register']");
    }

    // --- Actions ---

    public void fillPersonalDetails(String firstName, String lastName, String address,
                                    String city, String state, String zip,
                                    String phone, String ssn) {
        firstNameInput.fill(firstName);
        lastNameInput.fill(lastName);
        addressInput.fill(address);
        cityInput.fill(city);
        stateInput.fill(state);
        zipCodeInput.fill(zip);
        phoneInput.fill(phone);
        ssnInput.fill(ssn);
    }

    public void fillCredentials(String username, String password) {
        usernameInput.fill(username);
        passwordInput.fill(password);
        confirmPasswordInput.fill(password);
    }

    public void submit() {
        registerButton.click();
        page.waitForLoadState();
    }

    // --- Assertions / State ---

    public boolean isOnRegisterPage() {
        return page.url().contains("register");
    }

    public boolean isRegistrationSuccessful() {
        String content = page.locator("#rightPanel").textContent();
        return content.contains("Your account was created successfully");
    }

    public String getWelcomeMessage() {
        try {
            return page.locator("#rightPanel h1").textContent().trim();
        } catch (Exception e) {
            return page.locator("#rightPanel").textContent().trim();
        }
    }

    public int getValidationErrorCount() {
        return page.locator("span.error").count();
    }

    public boolean hasValidationErrors() {
        return getValidationErrorCount() > 0;
    }
}
