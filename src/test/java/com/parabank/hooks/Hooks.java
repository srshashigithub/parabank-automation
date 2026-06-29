package com.parabank.hooks;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.parabank.context.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

/**
 * Cucumber Hooks — executed before and after each scenario.
 * Opens a Chromium browser and a fresh page before each scenario;
 * closes everything cleanly after each scenario.
 *
 * TestContext is injected by PicoContainer so the same instance is shared
 * with all step definition classes in the same scenario.
 */
public class Hooks {

    private final TestContext context;

    public Hooks(TestContext context) {
        this.context = context;
    }

    @Before(order = 0)
    public void setUp(Scenario scenario) {
        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("  STARTING: " + scenario.getName());
        System.out.println("╚══════════════════════════════════════════════════╝");

        Playwright playwright = Playwright.create();

        Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setSlowMo(400)
        );

        Page page = browser.newPage();
        page.setViewportSize(1366, 768);

        context.setPlaywright(playwright);
        context.setBrowser(browser);
        context.setPage(page);
    }

    @After(order = 0)
    public void tearDown(Scenario scenario) {
        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.printf ("  FINISHED: %s | Status: %s%n",
                scenario.getName(), scenario.getStatus());
        System.out.println("╚══════════════════════════════════════════════════╝\n");

        if (context.getBrowser() != null) {
            context.getBrowser().close();
        }
        if (context.getPlaywright() != null) {
            context.getPlaywright().close();
        }
    }
}
