package com.parabank.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

/**
 * Page Object for ParaBank Accounts Overview Page.
 * URL: /parabank/overview.htm  (displayed after successful login)
 *
 * Responsibilities:
 *  - Verify the overview page is loaded
 *  - Read and print account numbers and balances from the accounts table
 */
public class AccountOverviewPage {

    private final Page page;

    public AccountOverviewPage(Page page) {
        this.page = page;
    }

    // --- Assertions / State ---

    public boolean isDisplayed() {
        String url = page.url();
        System.out.println("[DEBUG] Current URL after login: " + url);
        return url.contains("overview") || url.contains("index") || url.contains("parabank");
    }

    /**
     * Waits for the AJAX-loaded accounts table to become visible.
     * ParaBank loads account data asynchronously after the page shell renders,
     * so we wait for networkidle first, then for the table rows to appear.
     */
    public void waitUntilLoaded() {
        // Step 1: wait for all AJAX requests to settle
        page.waitForLoadState(LoadState.NETWORKIDLE,
                new Page.WaitForLoadStateOptions().setTimeout(30_000));

        // Step 2: wait for the accounts table itself
        try {
            page.waitForSelector("#accountTable",
                    new Page.WaitForSelectorOptions().setTimeout(20_000));
        } catch (Exception e) {
            System.out.println("[WARN] #accountTable not found; trying tbody rows directly.");
        }
    }

    // --- Data Retrieval ---

    /**
     * Reads every account row from the accounts table and prints:
     *   Account Number | Balance | Available Amount
     * Also prints the grand-total row at the bottom.
     */
    public void printAccountBalances() {
        waitUntilLoaded();

        System.out.println();
        System.out.println("==============================================");
        System.out.println("          ACCOUNT BALANCES OVERVIEW           ");
        System.out.println("==============================================");
        System.out.printf("%-20s %-15s %-20s%n", "Account Number", "Balance", "Available Amount");
        System.out.println("----------------------------------------------");

        Locator rows = page.locator("#accountTable tbody tr");
        int totalRows = rows.count();

        for (int i = 0; i < totalRows; i++) {
            Locator row = rows.nth(i);
            Locator cells = row.locator("td");
            int cellCount = cells.count();

            if (cellCount == 3) {
                // Regular account row: Account | Balance | Available
                String accountNo  = cells.nth(0).textContent().trim();
                String balance    = cells.nth(1).textContent().trim();
                String available  = cells.nth(2).textContent().trim();
                System.out.printf("%-20s %-15s %-20s%n", accountNo, balance, available);

            } else if (cellCount == 2) {
                // Total row: colspan="2" label | Total amount
                String label  = cells.nth(0).textContent().trim();
                String amount = cells.nth(1).textContent().trim();
                System.out.println("----------------------------------------------");
                System.out.printf("%-35s %-20s%n", label, amount);
            }
        }

        System.out.println("==============================================");
        System.out.println();
    }

    /**
     * Returns the total balance string from the last row of the accounts table.
     */
    public String getTotalBalance() {
        waitUntilLoaded();
        Locator rows = page.locator("#accountTable tbody tr");
        Locator lastRow = rows.last();
        return lastRow.locator("td").last().textContent().trim();
    }
}
