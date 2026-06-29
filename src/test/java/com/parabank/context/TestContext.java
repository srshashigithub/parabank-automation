package com.parabank.context;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

/**
 * Shared test context injected by Cucumber PicoContainer into every step
 * definition class and the Hooks class within the same scenario.
 *
 * Holds the Playwright runtime objects and any data that must travel between
 * step definition classes (e.g. dynamically generated credentials).
 */
public class TestContext {

    private Playwright playwright;
    private Browser browser;
    private Page page;

    // Registration data shared between sign-up and sign-in step definitions
    private String registeredUsername;
    private String registeredPassword;

    // --- Playwright runtime ---

    public Playwright getPlaywright()                  { return playwright; }
    public void setPlaywright(Playwright playwright)   { this.playwright = playwright; }

    public Browser getBrowser()                        { return browser; }
    public void setBrowser(Browser browser)            { this.browser = browser; }

    public Page getPage()                              { return page; }
    public void setPage(Page page)                     { this.page = page; }

    // --- Credentials ---

    public String getRegisteredUsername()                        { return registeredUsername; }
    public void setRegisteredUsername(String registeredUsername) { this.registeredUsername = registeredUsername; }

    public String getRegisteredPassword()                        { return registeredPassword; }
    public void setRegisteredPassword(String registeredPassword) { this.registeredPassword = registeredPassword; }
}
