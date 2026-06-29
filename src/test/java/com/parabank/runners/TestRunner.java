package com.parabank.runners;

import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

/**
 * JUnit Platform Suite runner for Cucumber.
 *
 * Configuration is driven by src/test/resources/junit-platform.properties:
 *   - cucumber.glue       → step definitions and hooks packages
 *   - cucumber.plugin     → reporting plugins
 *   - cucumber.features   → feature files classpath location
 *
 * Run all tests:   mvn test
 * Run with a tag:  mvn test -Dcucumber.filter.tags="@smoke"
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
public class TestRunner {
}
