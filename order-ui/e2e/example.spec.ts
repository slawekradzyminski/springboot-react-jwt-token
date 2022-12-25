import { test, expect } from "@playwright/test";

test.describe("Order UI", () => {
  test("can enter admin page", async ({ page }) => {
    await page.goto("http://localhost:3000");
    await expect(page).toHaveTitle("order-ui");
    await page.getByRole("link", { name: "Login" }).click();
    await page.locator("[name=username]").type("admin");
    await page.locator("[name=password]").type("admin");
    await page.getByRole("button", { name: "Login" }).click();
    await expect(page.getByRole("link", { name: "AdminPage" })).toBeVisible();
  });
});
