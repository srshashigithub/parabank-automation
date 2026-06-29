package com.parabank.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * TestNG runner for Cucumber BDD scenarios.
 *
 * Parallel execution is enabled via @DataProvider(parallel = true).
 * Thread count is controlled by testng.xml (data-provider-thread-count="3").
 *
 * Each scenario gets its own TestContext (injected by PicoContainer),
 * which holds a dedicated Playwright browser instance — so parallel
 * scenarios never share browser state.
 *
 * Run all tests:          mvn test
 * Run with a tag filter:  mvn test -Dcucumber.filter.tags="@smoke"
 */
@CucumberOptions(
        features = "classpath:features",
        glue     = {"com.parabank.hooks", "com.parabank.stepdefs"},
        plugin   = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json",
                "timeline:target/cucumber-reports/timeline",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        publish  = false
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
