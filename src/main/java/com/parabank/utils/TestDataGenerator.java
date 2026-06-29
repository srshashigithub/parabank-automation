package com.parabank.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class that generates test data for the ParaBank registration form.
 * Uses a timestamp suffix on usernames to guarantee uniqueness across test runs.
 */
public final class TestDataGenerator {

    private static final String DEFAULT_PASSWORD = "Test@1234";

    private TestDataGenerator() {}

    /** Generates a timestamp-based unique username, e.g. testuser0629104532 */
    public static String generateUniqueUsername() {
        String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMddHHmmss"));
        return "testuser" + ts;
    }

    public static String getDefaultPassword() {
        return DEFAULT_PASSWORD;
    }

    public static String getFirstName()  { return "Test"; }
    public static String getLastName()   { return "Automation"; }
    public static String getAddress()    { return "123 Automation Drive"; }
    public static String getCity()       { return "San Jose"; }
    public static String getState()      { return "CA"; }
    public static String getZipCode()    { return "95101"; }
    public static String getPhone()      { return "4081234567"; }
    public static String getSsn()        { return "987654321"; }
}
