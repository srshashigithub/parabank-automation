package com.parabank.hooks;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.parabank.context.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;

/**
 * Cucumber Hooks — executed before and after each scenario.
 *
 * Before: launches a Chromium browser and stores it in TestContext.
 * After:  attaches a screenshot to the Allure report if the scenario
 *         failed, then closes the browser and Playwright cleanly.
 *
 * TestContext is injected by PicoContainer — the same instance is shared
 * with all step definition classes in the same scenario, and a brand-new
 * instance is created for each parallel scenario thread.
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
        System.out.printf("  FINISHED: %s | Status: %s%n",
                scenario.getName(), scenario.getStatus());
        System.out.println("╚══════════════════════════════════════════════════╝\n");

        if (scenario.isFailed() && context.getPage() != null) {
            try {
                byte[] screenshot = context.getPage().screenshot(
                        new Page.ScreenshotOptions().setFullPage(true)
                );
                Allure.addAttachment(
                        "Screenshot — " + scenario.getName(),
                        "image/png",
                        new ByteArrayInputStream(screenshot),
                        "png"
                );
            } catch (Exception e) {
                System.out.println("[WARN] Could not capture screenshot: " + e.getMessage());
            }
        }

        if (context.getBrowser() != null) {
            context.getBrowser().close();
        }
        if (context.getPlaywright() != null) {
            context.getPlaywright().close();
        }
    }
}
