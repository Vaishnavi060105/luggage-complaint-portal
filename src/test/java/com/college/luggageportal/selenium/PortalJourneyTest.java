package com.college.luggageportal.selenium;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

/** The four critical user journeys. Run with: mvn test -Pselenium -Dbase.url=... */
@Tag("selenium")
class PortalJourneyTest extends BaseTest {

    /** Helper: fills the form with valid test data and returns the reference number shown. */
    private String submitValidComplaint() {
        driver.get(baseUrl + "/complaints/new");
        driver.findElement(By.id("passengerName")).sendKeys("Asha Verma");
        driver.findElement(By.id("email")).sendKeys("asha.verma@example.com");
        driver.findElement(By.id("phone")).sendKeys("9876543210");
        driver.findElement(By.id("flightNo")).sendKeys("AI202");
        driver.findElement(By.id("pnr")).sendKeys("AB12CD");
        driver.findElement(By.id("bagTag")).sendKeys("1234567890");
        new Select(driver.findElement(By.id("type"))).selectByValue("DELAYED");
        driver.findElement(By.id("description")).sendKeys("My suitcase did not arrive on flight AI202.");
        driver.findElement(By.id("submit")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("referenceNo")));
        return driver.findElement(By.id("referenceNo")).getText().trim();
    }

    @Test
    void j1_validComplaintShowsReferenceNumber() {
        String ref = submitValidComplaint();
        assertTrue(ref.matches("LCP-\\d{4}-\\d{6}"), "Unexpected reference format: " + ref);
    }

    @Test
    void j2_emptyFormShowsValidationErrors() {
        driver.get(baseUrl + "/complaints/new");
        driver.findElement(By.id("submit")).click();
        List<WebElement> errors = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".error")));
        assertTrue(errors.size() >= 5, "Expected several validation errors but found " + errors.size());
    }

    @Test
    void j3_trackingShowsStatus() {
        String ref = submitValidComplaint();
        driver.get(baseUrl + "/track");
        driver.findElement(By.id("trackRef")).sendKeys(ref);
        driver.findElement(By.id("trackSubmit")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("statusValue")));
        assertEquals("SUBMITTED", driver.findElement(By.id("statusValue")).getText().trim());
    }

    @Test
    void j4_reviewerApprovesComplaint() {
        String ref = submitValidComplaint();

        driver.get(baseUrl + "/login");
        driver.findElement(By.id("username")).sendKeys("reviewer1");
        driver.findElement(By.id("password")).sendKeys("Review@123");
        driver.findElement(By.id("loginSubmit")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.linkText(ref))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("decision")));
        new Select(driver.findElement(By.id("decision"))).selectByValue("APPROVE");
        driver.findElement(By.id("comment")).sendKeys("Verified with baggage desk. Approved.");
        driver.findElement(By.id("decisionSubmit")).click();

        wait.until(ExpectedConditions.textToBe(By.id("statusValue"), "APPROVED"));
        assertEquals("APPROVED", driver.findElement(By.id("statusValue")).getText().trim());
    }
}
