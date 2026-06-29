package com.parabank.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * Page Object for ParaBank Home Page.
 * Covers: Login form and navigation to Register.
 */
public class HomePage {

    private final Page page;

    private static final String HOME_URL =
            "https://parabank.parasoft.com/parabank/index.htm?ConnType=JDBC";
    private static final String LOGOUT_URL =
            "https://parabank.parasoft.com/parabank/logout.htm";

    // --- Locators ---
    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator loginButton;
    private final Locator registerLink;
    private final Locator loginErrorMessage;

    public HomePage(Page page) {
        this.page = page;
        this.usernameInput    = page.locator("input[name='username']");
        this.passwordInput    = page.locator("input[name='password']");
        this.loginButton      = page.locator("input[value='Log In']");
        this.registerLink     = page.locator("a[href*='register']").first();
        this.loginErrorMessage = page.locator("p.error");
    }

    // --- Actions ---

    public void navigate() {
        page.navigate(HOME_URL);
        page.waitForLoadState();
    }

    public void logout() {
        page.navigate(LOGOUT_URL);
        page.waitForLoadState();
    }

    public void clickRegister() {
        registerLink.click();
        page.waitForLoadState();
    }

    public void login(String username, String password) {
        usernameInput.fill(username);
        passwordInput.fill(password);
        loginButton.click();
        page.waitForLoadState();
    }

    // --- Assertions / State ---

    public boolean isLoaded() {
        return page.url().contains("parabank");
    }

    public boolean isLoginErrorDisplayed() {
        return loginErrorMessage.isVisible();
    }

    public String getLoginErrorText() {
        return loginErrorMessage.textContent().trim();
    }
}
