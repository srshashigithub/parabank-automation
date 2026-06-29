package com.parabank.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Generates test data for the ParaBank registration form.
 * Static values are read from config.properties.
 * Usernames use a timestamp suffix to guarantee uniqueness across runs.
 */
public final class TestDataGenerator {

    private TestDataGenerator() {}

    /** Generates a timestamp-based unique username, e.g. testuser0629104532 */
    public static String generateUniqueUsername() {
        String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMddHHmmss"));
        return "testuser" + ts;
    }

    public static String getDefaultPassword()  { return ConfigReader.get("test.default.password"); }
    public static String getFirstName()        { return ConfigReader.get("test.first.name"); }
    public static String getLastName()         { return ConfigReader.get("test.last.name"); }
    public static String getAddress()          { return ConfigReader.get("test.address"); }
    public static String getCity()             { return ConfigReader.get("test.city"); }
    public static String getState()            { return ConfigReader.get("test.state"); }
    public static String getZipCode()          { return ConfigReader.get("test.zip.code"); }
    public static String getPhone()            { return ConfigReader.get("test.phone"); }
    public static String getSsn()              { return ConfigReader.get("test.ssn"); }
}
